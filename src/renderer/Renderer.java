package renderer;

import model.Line;
import model.Point;
import view.Raster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Renderer {

    private final Raster raster;

    public Renderer(Raster raster) {
        this.raster = raster;
    }

    public void clear() {
        raster.clear();
    }

    public void drawLine(int x1, int y1, int x2, int y2, int color) {
        int temp;
        int dx = x1 - x2;
        int dy = y1 - y2;
        float k = (y2 - y1) / (float) (x2 - x1);
        float q = y1 - k * x1;
        if (x2 - x1 == 0) {
           if (y1 > y2) {
               temp = y1;
               y1 = y2;
               y2 = temp;
           }
           for (int y = y1; y <= y2; y++) {
               raster.drawPixel(x1, y, color);
           }
        } else if (Math.abs(dy) < Math.abs(dx)) {
            if (x2 < x1) {
                temp = x1;
                x1 = x2;
                x2 = temp;
            }
            for (int x = x1; x <= x2; x++) {
                float y = k * x + q;
                raster.drawPixel(x, Math.round(y), color);
            }
        } else {
            if (y2 < y1) {
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
            for (int y = y1; y <= y2; y++) {
                float x = (y - q) / k;
                raster.drawPixel(Math.round(x), y, color);
            }
        }
    }

    public void drawLineDDA(int x1, int y1, int x2, int y2, int color) {
        int dx, dy, err = 0;
        dx = x2 - x1;
        dy = y2 - y1;
        if (dx >= 0 && dy >= 0) {
            do {
                raster.drawPixel(x1, y1, color);
                if (err > 0) {
                    x1 = x1 + 1;
                    err = err - dy;
                } else {
                    y1 = y1 + 1;
                    err = err + dx;
                }
            } while ((x1 <= x2) && (y1 <= y2));
        } else if (dx <= 0 && dy <= 0) {
            do {
                raster.drawPixel(x1, y1, color);
                if (err > 0) {
                    x1 = x1 - 1;
                    err = err + dy;
                } else {
                    y1 = y1 - 1;
                    err = err - dx;
                }
            } while ((x1 >= x2) && (y1 >= y2));
        } else if (dx >= 0) {
            do {
                raster.drawPixel(x1, y1, color);
                if (err < 0) {
                    x1 = x1 + 1;
                    err = err - dy;
                } else {
                    y1 = y1 - 1;
                    err = err - dx;
                }
            } while ((x1 <= x2) && (y1 >= y2));
        } else {
            do {
                raster.drawPixel(x1, y1, color);
                if (err < 0) {
                    x1 = x1 - 1;
                    err = err + dy;
                } else {
                    y1 = y1 + 1;
                    err = err + dx;
                }
            } while ((x1 >= x2) && (y1 <= y2));
        }
    }

    public void drawPolygon(List<Point> points, int color) {
        for (int i = 1; i < points.size(); i++) {
            drawLine(points.get(i - 1).getX(), points.get(i - 1).getY(), points.get(i).getX(), points.get(i).getY(), color);
        }
        drawLine(points.get(0).getX(), points.get(0).getY(), points.get(points.size() - 1).getX(), points.get(points.size() - 1).getY(), color);
    }

    public void seedFillBackground(int x, int y, int color) {
        if (raster.getPixelColor(x, y) != color) {
            raster.drawPixel(x, y, color);
        }
        if (raster.getPixelColor(x + 1, y) != color) {
            seedFillBackground(x + 1, y, color);
        }
        if (raster.getPixelColor(x - 1, y) != color) {
            seedFillBackground(x - 1, y, color);
        }
        if (raster.getPixelColor(x, y + 1) != color) {
            seedFillBackground(x, y + 1, color);
        }
        if (raster.getPixelColor(x, y - 1) != color) {
            seedFillBackground(x, y - 1, color);
        }
    }

    public void seedFillBorder(int x, int y, int color) {
        if (raster.getPixelColor(x, y) != color) {
            raster.drawPixel(x, y, color);
            seedFillBorder(x + 1, y, color);
            seedFillBorder(x - 1, y, color);
            seedFillBorder(x, y + 1, color);
            seedFillBorder(x, y - 1, color);
        }
    }

    public void scanLine(List<Point> cuted, int color) {
        List<Line> lines = new ArrayList<>();
        Point point1 = cuted.get(cuted.size() - 1);
        for (Point point2: cuted) {
            lines.add(new Line(point1, point2));
            point1 = point2;
        }

        int yMax = lines.get(0).getP1().getY();
        int yMin = lines.get(0).getP1().getY();
        List<Line> pUsecky = new ArrayList<>();

        for (Line line: lines) {
            if (line.getP1().getY() != line.getP2().getY()) {
                Line pUs = new Line(new Point(line.getP1().getX(), line.getP1().getY()), new Point(line.getP2().getX(), line.getP2().getY()));
                if (line.getP1().getY() > line.getP2().getY()) {
                    pUs.prohod();
                }
                pUs.vypocti();
                pUsecky.add(pUs);

                if (yMin > pUs.getP1().getY()) {
                    yMin = pUs.getP1().getY();
                }
                if (yMax < pUs.getP2().getY()) {
                    yMax = pUs.getP2().getY();
                }
            }
        }

        for (int y = yMin + 1; y < yMax; y++) {
            List<Integer> pruseciky = new ArrayList<>();
            for (Line usecka: pUsecky) {
                if (y >= usecka.getP1().getY() && y < usecka.getP2().getY()) {
                    pruseciky.add(usecka.prusecik(y));
                }
            }
            Collections.sort(pruseciky);
            if (pruseciky.size() > 1) {
                for (int i = 0; i < pruseciky.size(); i += 2) {
                    drawLine(pruseciky.get(i), y, pruseciky.get(i + 1), y, color);
                }
            }
        }
    }
}
