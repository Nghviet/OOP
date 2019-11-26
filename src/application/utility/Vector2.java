package application.utility;

public class Vector2 {
    private double x;  //HORIZONTAL
    private double y;  //VERTICAL

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 that) {
        this.x = that.x;
        this.y = that.y;
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

    public double angleTo(Vector2 that) {
        double x = this.getX() - that.getX();
        double y = this.getY() - that.getY();

        double baseRotation = Math.abs(Math.toDegrees(Math.asin(y/Math.sqrt(x*x+y*y))));
        if(x<0 && y<0) baseRotation +=180;
        if(x<0 && y>0) baseRotation = 180 - baseRotation;
        if(x>0 && y<0) baseRotation = 360 - baseRotation;
        if(x==0) {
            if(y>0) baseRotation = 90;
            else baseRotation = 270;
        }
        if(y==0) {
            if(x>0) baseRotation = 0;
            else baseRotation = 180;
        }
        return baseRotation;
    }
}
