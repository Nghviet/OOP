package application.utility;

public class Vector2 {
    private double x;  //HORIZONTAL
    private double y;  //VERTICAL

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }

    public void setX(int x) { this.x = x; }

    public double getY() { return y; }

    public void setY(int y) { this.y = y; }

    public Vector2 minus(Vector2 that) {
        return new Vector2(this.x-that.x,this.y-that.y);
    }

    public double distanceTo(Vector2 that) {
        return Math.sqrt((this.x-that.x)*(this.x-that.x) + (this.y-that.y)*(this.y-that.y));
    }

    public void normalize() {
        double l = x*x+y*y;
        x = x/Math.sqrt(l);
        y = y/Math.sqrt(l);
    }
}
