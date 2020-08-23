import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;


public class FastCollinearPoints {

    private int numberOfSegments;
    private ArrayList<LineSegment> segments;


    public FastCollinearPoints(Point[] points) {
        segments = new ArrayList<LineSegment>();
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
            int rep = 0;
            ArrayList<Point> pss = new ArrayList<Point>();
            for (Point q : points) {
                if (q == null) throw new IllegalArgumentException();
                if (q.compareTo(p) == 0) {
                    if (rep == 1) throw new IllegalArgumentException();
                    rep++;
                    continue;
                }
                pss.add(q);
            }
            //STORE pss.sort(p.sloperOrder())
            pss.sort(p.slopeOrder());
            int c = 0;
            while (c < pss.size()) {
                double curS = p.slopeTo(pss.get(c));
                ArrayList<Point> tmp = new ArrayList<Point>();
                while (c < pss.size() && curS == p.slopeTo(pss.get(c))) {
                    tmp.add(pss.get(c));
                    c++;
                }
                tmp.add(p);
                tmp.sort(Point::compareTo);
                if (tmp.size() >= 4) {
                    //must check if
                    if (tmp.get(0).compareTo(p) == 0) {
                        segments.add(new LineSegment(tmp.get(0), tmp.get(tmp.size() - 1)));
                        numberOfSegments += 1;
                    }

                }
            }

        }
    }

    public int numberOfSegments() {
        return this.numberOfSegments;

    }

    public LineSegment[] segments() {
        return this.segments.toArray(new LineSegment[this.numberOfSegments]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }


        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdOut.println(collinear.numberOfSegments());
        StdDraw.show();
    }
}
