public class Range {
    public double minx;
    public double maxx;
    public double miny;
    public double maxy;

    public Range(double minx, double maxx, double miny, double maxy) {
        this.minx = minx;
        this.maxx = maxx;
        this.miny = miny;
        this.maxy = maxy;
    }

    boolean contains(Point p){
        if(p.x>=minx && p.x<=maxx && p.y>=miny && p.y<=maxy) return true;
        return false;
    }

    boolean Absorbed(node n){
        double X = n.points.get(0).x;
        double x = n.points.get(0).x;
        double Y = n.points.get(0).y;
        double y = n.points.get(0).y;

        for(int i=1;i<n.points.size();i++){
            Point p = n.points.get(i);
            if(p.x>X) X = p.x;
            if(p.x<x) x = p.x;
            if(p.y>Y) Y = p.y;
            if(p.y<y) y = p.y;
        }

        if(minx<=x && miny<=y && maxx>=X && maxy>=Y) return true;

        return false;
    }

    boolean Intersects(node n){
        if(n==null) return false;
        double X = n.points.get(0).x;
        double x = n.points.get(0).x;
        double Y = n.points.get(0).y;
        double y = n.points.get(0).y;

        for(int i=1;i<n.points.size();i++){
            Point p = n.points.get(i);
            if(p.x>X) X = p.x;
            if(p.x<x) x = p.x;
            if(p.y>Y) Y = p.y;
            if(p.y<y) y = p.y;
        }

        if((x>=minx && x<=maxx && X>=maxx) || (y>=miny && y<=maxy && Y>=maxx) ||
                (X>=minx && X<=maxx && x<=minx) || (Y>miny && Y<=maxy && y<=miny)) return true;

        return false;
    }
}
