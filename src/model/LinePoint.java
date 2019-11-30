package model;

public class LinePoint extends Point {
    private int type;

    public LinePoint (int x, int y, int type) {
        super(x, y);
        this.type = type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
