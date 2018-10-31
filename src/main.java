import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Vector;

public class main {
    public static void main(String[] args) {
        Vector<Point> points = new Vector<>();

        try {
            Scanner input = new Scanner(new File("Graphics//input.txt"));
            int n = input.nextInt();

            for(int i=0;i<n;i++){
                double x = input.nextDouble();
                double y = input.nextDouble();
                Point p = new Point(x,y);
                points.add(p);
            }
            //input.close();
            KD_Tree t = new KD_Tree();
            t.root = t.Build_Tree(points,0);
            t.in_order(t.root);
            System.out.println();
            t.fraternize(t.root);
            t.printParent(t.root);
            int k = 0;
            int j = 0;

            while (input.hasNextLine()){
                String s = input.next();
                //System.out.println(s);
                if(s.contains("R")){
                    double x = input.nextDouble();
                    double y = input.nextDouble();

                    double x1 = input.nextDouble();
                    double y1 = input.nextDouble();

                    double x_max = Double.max(x,x1);
                    double x_min = Double.min(x,x1);
                    double y_max = Double.max(y,y1);
                    double y_min = Double.min(y,y1);

                    Range R = new Range(x_min,x_max,y_min,y_max);
                    Vector<Point> soln = new Vector<>();

                    PrintWriter out = new PrintWriter(new File("Graphics//range_"+k+".txt"));
                    k++;
                    out.println(x_max+" "+y_max);
                    out.println(x_min+" "+y_max);
                    out.println(x_min+" "+y_min);
                    out.println(x_max+" "+y_min);

                    out.close();

                    t.Range_Query(soln,t.root,R);
                    for(int i=0;i<soln.size();i++){
                        Point r = soln.get(i);
                        System.out.print("("+r.x+","+r.y+") ");
                    }
                    System.out.println();
                    System.out.println(soln.size());

                    //System.out.println(s+" "+p.x+" "+p.y+" "+p1.x +" "+p1.y);
                }
                else if(s.contains("N")){
                    double x = input.nextDouble();
                    double y = input.nextDouble();
                    Point q = new Point(x,y);
                    Point sol;

                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(5);


                    sol = t.Nearest_Neighbor(q,t.root,points.get(0),Double.MAX_VALUE);
                    double ans = t.distance(sol,q);
                    System.out.println(df.format(ans)+" ("+sol.x+","+sol.y+")");

                    PrintWriter out = new PrintWriter(new File("Graphics//NN_"+j+".txt"));
                    j++;
                    out.println(q.x+" "+q.y+" "+ans);

                    out.close();
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }
}
