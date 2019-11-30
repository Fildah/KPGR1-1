package model;

public class Line {
    private Point p1;
    private Point p2;

    private float k, q;

    public Line (Point point1, Point point2) {
        this.p1 = point1;
        this.p2 = point2;
    }

    public void prohod () {
        Point temp = p1;
        p1 = p2;
        p2 = temp;
    }

    public void vypocti() {
        k = (p2.getX() - p1.getX()) / (float) (p2.getY() - p1.getY());
        q = p1.getX() - k * p1.getY();
    }

    public void zkrat() {
        p2.setY(p2.getY() - 1);
    }

    public int prusecik(int y) {
        return Math.round(this.k * y + this.q);
    }

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public float getK() {
        return k;
    }

    public float getQ() {
        return q;
    }
}
