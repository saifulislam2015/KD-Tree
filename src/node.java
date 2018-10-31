import java.util.Vector;

public class node {
    public double l;
    public Vector<Point> points;
    public node left;
    public node right;
    public node parent;
    public String direction;
    public KD_Tree tree;

    public node(double l, Vector<Point> points, node left, node right,String s,KD_Tree t) {
        this.l = l;
        this.points = points;
        this.left = left;
        this.right = right;
        this.direction = s;
        this.tree = t;
    }

    public node(Vector<Point> points) {
        //this.l = points.get(0).x;
        this.points = points;
        this.left = null;
        this.right = null;
    }

    public void setParent(node parent) {
        this.parent = parent;
    }
}
