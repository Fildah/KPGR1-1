package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegularPoly{
    private int x, y, x2, y2, apex;
    private double degree;
    private List<Point> points = new ArrayList<>();

    public RegularPoly(int x, int y, int x2, int y2, int apex) {
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        this.apex = Math.max(apex, 3);
        calculate();
    }

    public List<Point> getPoints() {
        return points;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
        calculate();
    }

    public void setY2(int y2) {
        this.y2 = y2;
        calculate();
    }

    public int getApex() {
        return apex;
    }

    public void setApex(int apex) {
        this.apex = Math.max(apex, 3);
        calculate();
    }

    private void calculate() {
        this.points = new ArrayList<>();
        this.points.add(new Point(this.x2, this.y2));
        this.degree = Math.toRadians(360.0 / this.apex);
        for (int i = 1; i < this.apex; i++) {
            double newX = this.x + (Math.cos(this.degree * i) * (this.x2 - this.x) + Math.sin(this.degree * i) * (this.y2 - this.y));
            double newY = this.y + (- Math.sin(this.degree * i) * (this.x2 - this.x) + Math.cos(this.degree * i) * (this.y2 - this.y));
            this.points.add(new Point((int) Math.round(newX), (int) Math.round(newY)));
        }
    }
}
