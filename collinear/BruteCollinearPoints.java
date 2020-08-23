import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private List<LineSegment> segArr;
    private int numSegs;

    public BruteCollinearPoints(Point[] points) {
        this.numSegs = 0;
        this.segArr = new ArrayList<LineSegment>();
        if (points == null) throw new IllegalArgumentException();
        int pLen = points.length;
        int p = 0;//3rd
        int q = 1;//4th
        int r = 2;//5th
        int s = 3;//6th

        if (pLen < 4) {
            for (int i = 0; i < pLen; i++) {
                if (points[i] == null) throw new IllegalArgumentException();
                for (int j = 0; j < pLen; j++) {
                    if (j == i) continue;
                    if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
                }
            }
            return;
        }
        while (p < pLen - 3) {
            if (points[p] == null) throw new IllegalArgumentException();
            while (q < pLen - 2) {
                if (points[q] == null) throw new IllegalArgumentException();
                while (r < pLen - 1) {
                    if (points[r] == null) throw new IllegalArgumentException();
                    while (s < pLen) {
                        if (points[p].compareTo(points[q]) == 0 || points[p].compareTo(points[r]) == 0 || points[p].compareTo(points[s]) == 0 || points[q].compareTo(points[r]) == 0 || points[q].compareTo(points[s]) == 0 || points[r].compareTo(points[s]) == 0) {
                            throw new IllegalArgumentException();
                        }
                        if (points[s] == null)
                            throw new IllegalArgumentException();
                        if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r]) && points[p].slopeTo(points[q]) == points[p].slopeTo(points[s]) && points[p].slopeTo(points[r]) == points[p].slopeTo(points[s]) && points[q].slopeTo(points[r]) == points[p].slopeTo(points[s])) {
                            Point[] pls = {points[p], points[q], points[r], points[s]};
                            Arrays.sort(pls);
                            LineSegment tmp = new LineSegment(pls[0], pls[3]);
                            this.segArr.add(tmp);
                            this.numSegs++;
                        }
                        s++;
                    }
                    r++;
                    s = r + 1;
                }
                q++;
                r = q + 1;
                s = r + 1;
            }
            p++;
            q = p + 1;
            r = q + 1;
            s = r + 1;
        }
    }


    public int numberOfSegments() {
        return this.numSegs;
    }


    public LineSegment[] segments() {
        return this.segArr.toArray(new LineSegment[this.numSegs]);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        System.out.println(collinear.numberOfSegments());
        StdDraw.show();
    }
}
