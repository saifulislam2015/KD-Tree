public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    boolean Contained(Point p,Point q){
        double min_x = Double.min(p.x,q.x);
        double max_x = Double.max(p.x,q.x);

        double min_y = Double.min(p.y,q.y);
        double max_y = Double.max(p.y,q.y);

        if(this.x>=min_x && this.x<=max_x && this.y>=min_y && this.y<=max_y) return true;

        return false;
    }
}
