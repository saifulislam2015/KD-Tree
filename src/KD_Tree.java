import java.util.Collections;
import java.util.Vector;

public class KD_Tree {
    public node root;

    public KD_Tree() {
        this.root = null;
    }

    public double median_x(Vector<Point> P){
        Vector<Double> x = new Vector<>();

        for(int i=0;i<P.size();i++) x.add(P.get(i).x);
        Collections.sort(x);

        //for(int i=0;i<x.size();i++) System.out.print(x.get(i)+" ");

        int mid = x.size()/2;

        if (x.size()%2 == 1) {
            return x.get(mid);
        }
        else {
            return (x.get(mid-1) + x.get(mid)) / 2;
        }
    }

    public double median_y(Vector<Point> P){
        Vector<Double> x = new Vector<>();

        for(int i=0;i<P.size();i++) x.add(P.get(i).y);
        Collections.sort(x);

        //for(int i=0;i<x.size();i++) System.out.print(x.get(i)+" ");

        int mid = x.size()/2;

        if (x.size()%2 == 1) {
            return x.get(mid);
        }
        else {
            return (x.get(mid-1) + x.get(mid)) / 2;
        }
    }

    public node Build_Tree(Vector<Point> P,int depth){
        Vector<Point> P1,P2;
        P1 = new Vector<>();
        P2 = new Vector<>();
        node left,right;
        double l;
        String s;

        if(P.size()==1){
            //System.out.println("here");
            node n = new node(P);
            return n;
        }

        else if(depth%2==0){
            l = median_x(P);
            s = "horizontal";
            //System.out.println("left child: "+ l);
            for(int i=0;i<P.size();i++){
                if(P.get(i).x<=l) P1.add(P.get(i));
                else P2.add(P.get(i));
            }
            left = Build_Tree(P1,depth+1);
            right = Build_Tree(P2,depth+1);
        }

        else{
            l = median_y(P);
            s = "vertical";
            //System.out.println("right child: "+ l);
            for(int i=0;i<P.size();i++){
                if(P.get(i).y<=l) P1.add(P.get(i));
                else P2.add(P.get(i));
            }
            left = Build_Tree(P1,depth+1);
            right = Build_Tree(P2,depth+1);
        }
        node root = new node(l,P,left,right,s,this);
        left.setParent(root);
        right.setParent(root);
        return root;
    }

    public void fraternize(node root){
        if(root==null) return;
        node l,r;
        if(root.left!=null){
            l = root.left;

            if(l.right==null && l.left==null){
                l.setParent(root);
            }
            fraternize(root.left);
        }
        if(root.right!=null) {
            r = root.right;

            if(r.right==null && r.left==null){
                    r.setParent(root);
            }
            fraternize(root.right);
        }
    }

    public void Range_Query(Vector<Point> soln,node root,Range R){
        if(root==null) return;

        if(root.right==null && root.left==null){
            if(R.contains(root.points.get(0))) soln.add(root.points.get(0));
        }
        //if(root.left!=null) Range_Query(soln,root.left,R);
        //if(root.right!=null) Range_Query(soln,root.right,R);
        if(R.Absorbed(root)) soln.addAll(root.points);
        else if(R.Intersects(root.right)) Range_Query(soln,root.right,R);
        else if(R.Intersects(root.left)) Range_Query(soln,root.left,R);
    }


    double distance(Point a,Point b){
        return Math.sqrt(Math.pow((a.x-b.x),2) + Math.pow((a.y-b.y),2));
    }

    public Point Nearest_Neighbor(Point q,node n,Point p,double w){
        //System.out.println("Inside: "+n.l);
        if(n.right==null && n.left==null){
            double w1 = distance(q,n.points.get(0));
            /*System.out.println(n.points.get(0).x+" "+n.points.get(0).y);
            System.out.println(q.x+" "+q.y);
            System.out.println("w1: "+w1);*/
            if(w1<w){
                //System.out.println("here:"+w1);
                w = w1;
                p = n.points.get(0);
                return p;
            }
        }
        else {
            double val;
            if(n.direction=="horizontal") val = q.x;
            else val = q.y;

            if(val<=n.l){
                if(val-w<=n.l) return Nearest_Neighbor(q,n.left,p,w);
                if(val+w>n.l) return Nearest_Neighbor(q,n.right,p,w);
            }
            else {
                if(val+w>n.l) return Nearest_Neighbor(q,n.right,p,w);
                if(val-w<=n.l) return Nearest_Neighbor(q,n.left,p,w);
            }
        }

        return null;

    }

    public void in_order(node root){

        if(root!=null){
            if (root.left==null && root.right==null)System.out.print(" ("+root.points.get(0).x+","+root.points.get(0).y+") ");
            //System.out.println();
            //System.out.println(" ("+root.points.get(0).x+","+root.points.get(0).y+") ");
            in_order(root.left);
            in_order(root.right);
        }
    }

    void printParent(node root){
        if(root!=null){
            if(root.parent==null) System.out.println(root.l);
            else {
                if(root.points.size()==1) System.out.println(root.points.get(0).x+" -> "+root.parent.l);
                else System.out.println(root.l+ " -> "+root.parent.l);
            }
            printParent(root.left);
            printParent(root.right);
        }
    }
}
