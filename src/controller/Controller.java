package controller;

import model.LinePoint;
import model.Point;
import model.RegularPoly;
import renderer.Renderer;
import view.Raster;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private final Renderer renderer;
    private List<LinePoint> linePoints;
    private List<Point> polyPoints;
    private RegularPoly regularPoly;
    private int origX, origY;
    private int lastDraw;
    private final List<Point> drawSize;

    public Controller(Raster raster) {
        this.renderer = new Renderer(raster);
        linePoints = new ArrayList<>();
        polyPoints = new ArrayList<>();
        regularPoly = null;
        initListeners(raster);
        drawSize = new ArrayList<>();
        drawSize.add(new Point(0, raster.getScreenSize().height - 1));
        drawSize.add(new Point(raster.getScreenSize().width - 1, raster.getScreenSize().height - 1));
        drawSize.add(new Point(raster.getScreenSize().width - 1, 0));
        drawSize.add(new Point(0, 0));
    }

    private void initListeners(Raster raster) {
        raster.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    polyPoints.add(new Point(e.getX(), e.getY()));
                    reDraw();
                } else if (e.getButton() == MouseEvent.BUTTON3 && lastDraw == 5) {
                    lastDraw = 6;
                    reDraw();
                } else if (e.getButton() == MouseEvent.BUTTON3 && lastDraw == 4) {
                    lastDraw = 5;
                    regularPoly.setX2(e.getX());
                    regularPoly.setY2(e.getY());
                } else if (e.getButton() == MouseEvent.BUTTON3 && e.isControlDown()) {
                    renderer.seedFillBackground(e.getX(), e.getY(), Color.WHITE.getRGB());
                } else if (e.getButton() == MouseEvent.BUTTON3 && e.isShiftDown()) {
                    renderer.seedFillBorder(e.getX(), e.getY(), Color.WHITE.getRGB());
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    lastDraw = 4;
                    regularPoly = new RegularPoly(e.getX(), e.getY(), e.getX(), e.getY(), 3);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                origX = e.getX();
                origY = e.getY();
                if (e.getButton() == MouseEvent.BUTTON1 && e.isShiftDown()) {
                    lastDraw = 1;
                    linePoints.add(new LinePoint(e.getX(), e.getY(), lastDraw));
                } else if (e.getButton() == MouseEvent.BUTTON1 && e.isControlDown()) {
                    lastDraw = 2;
                    linePoints.add(new LinePoint(e.getX(), e.getY(), lastDraw));
                }else if (e.getButton() == MouseEvent.BUTTON1) {
                    lastDraw = 3;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (lastDraw == 1 || lastDraw == 2) {
                        int type = linePoints.get(linePoints.size() - 1).getType();
                        linePoints.add(new LinePoint(e.getX(), e.getY(), type));
                    } else if (lastDraw == 3) {
                        if (polyPoints.size() < 1) {
                            polyPoints.add(new Point(origX, origY));
                        }
                        polyPoints.add(new Point(e.getX(), e.getY()));
                    }
                    reDraw();
                }
            }
        });

        raster.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                reDraw();
                if (e.isShiftDown() && (e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
                    renderer.drawLine(origX, origY, e.getX(), e.getY(), Color.RED.getRGB());
                } else if (e.isControlDown() && (e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
                    renderer.drawLineDDA(origX, origY, e.getX(), e.getY(), Color.RED.getRGB());
                } else if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
                    if (polyPoints.size() < 1) {
                        renderer.drawLine(origX, origY, e.getX(), e.getY(), Color.RED.getRGB());
                    } else {
                        renderer.drawLine(polyPoints.get(0).getX(), polyPoints.get(0).getY(), e.getX(), e.getY(), Color.RED.getRGB());
                        renderer.drawLine(polyPoints.get(polyPoints.size() - 1).getX(), polyPoints.get(polyPoints.size() - 1).getY(), e.getX(), e.getY(), Color.RED.getRGB());
                    }

                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (lastDraw == 4) {
                    regularPoly.setX2(e.getX());
                    regularPoly.setY2(e.getY());
                    reDraw();
                    renderer.drawLine(regularPoly.getX(), regularPoly.getY(), e.getX(), e.getY(), Color.RED.getRGB());
                } else if (lastDraw == 5) {
                    int dx = Math.abs(regularPoly.getX2() - e.getX());
                    int dy = Math.abs(regularPoly.getY2() - e.getY());
                    regularPoly.setApex((int) (Math.round((dx + dy) / 20.0)));
                    reDraw();
                    renderer.drawLine(regularPoly.getX(), regularPoly.getY(), regularPoly.getX2(), regularPoly.getY2(), Color.RED.getRGB());
                }
            }
        });

        raster.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 127) {
                    raster.clear();
                    linePoints = new ArrayList<>();
                    polyPoints = new ArrayList<>();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void reDraw() {
        renderer.clear();
        // Polygon
        if (polyPoints.size() > 1) {
            renderer.drawPolygon(polyPoints, Color.WHITE.getRGB());
        }
        // Regular Polygon
        if (regularPoly != null) {
            List<Point> cuted = cutPoly(regularPoly.getPoints(), drawSize);
            renderer.drawPolygon(cuted, Color.WHITE.getRGB());
        }
        // Line
        if (linePoints.size() > 1)
        for (int i = 1; i < linePoints.size(); i += 2) {
            if (linePoints.get(i).getType() == 1){
                renderer.drawLine(linePoints.get(i - 1).getX(), linePoints.get(i - 1).getY(), linePoints.get(i).getX(), linePoints.get(i).getY(), Color.WHITE.getRGB());
            } else if (linePoints.get(i).getType() == 2) {
                renderer.drawLineDDA(linePoints.get(i - 1).getX(), linePoints.get(i - 1).getY(), linePoints.get(i).getX(), linePoints.get(i).getY(), Color.WHITE.getRGB());
            }
        }
        if ((polyPoints.size() > 1) && (regularPoly != null)) {
            List<Point> cuted = cutPoly(polyPoints, regularPoly.getPoints());
            if (cuted.size() > 2) {
                renderer.scanLine(cuted, Color.YELLOW.getRGB());
                renderer.drawPolygon(cuted, Color.ORANGE.getRGB()+150);
            }
        }
    }

    private Point cross(Point point1, Point point2, Point point3, Point point4) {
        double a1 = point2.getY() - point1.getY();
        double b1 = point1.getX() - point2.getX();
        double c1 = a1*(point1.getX()) + b1*(point1.getY());
        double a2 = point4.getY() - point3.getY();
        double b2 = point3.getX() - point4.getX();
        double c2 = a2*(point3.getX())+ b2*(point3.getY());

        double x = (b2*c1 - b1*c2)/(a1*b2 - a2*b1);
        double y = (a1*c2 - a2*c1)/(a1*b2 - a2*b1);
        return new Point((int) Math.round(x), (int) Math.round(y));
    }

    private List<Point> cutPoly(List<Point> cliped, List<Point> cliping) {
        List<Point> in = new ArrayList<>(cliped);
        List<Point> out = new ArrayList<>();
        Point edge1 = cliping.get(cliping.size() - 1);
        for (Point edge2 : cliping) {
            out.clear();
            Point v1 = in.get(in.size() - 1);
            for (Point v2 : in) {
                if (inOrOut(v2, edge1, edge2)) {
                    if (!inOrOut(v1, edge1, edge2)) {
                        out.add(cross(v1, v2, edge1, edge2));
                    }
                    out.add(v2);
                } else {
                    if (inOrOut(v1, edge1, edge2)) {
                        out.add(cross(v1, v2, edge1, edge2));
                    }
                }
                v1 = v2;
            }
            in.clear();
            in.addAll(out);
            if (in.size() == 0) {
                break;
            }
            edge1 = edge2;
        }
        return in;
    }

    private boolean inOrOut(Point point0, Point edge1, Point edge2) {
        int result = (edge2.getY() - edge1.getY())  * point0.getX() -  (edge2.getX() - edge1.getX()) * point0.getY() + edge2.getX() * edge1.getY() - edge2.getY() * edge1.getX();
        return result >= 0;
    }
}
