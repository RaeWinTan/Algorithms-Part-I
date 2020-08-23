/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    public Point(int x, int y) {

        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }


    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        if (that == null) throw new IllegalArgumentException();
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (that == null) throw new IllegalArgumentException();
        /* YOUR CODE HERE */
        if (that.x == x && that.y == y) {
            return Double.NEGATIVE_INFINITY;
        }
        if (that.x == x)
            return Double.POSITIVE_INFINITY;
        if (that.y == y)
            return +0;
        return Double.valueOf(that.y - y) / Double.valueOf(that.x - x);
    }

    public int compareTo(Point that) {
        if (that == null) throw new IllegalArgumentException();
        /* YOUR CODE HERE */
        if (y < that.y) {
            return -1;
        } else if (y > that.y) {
            return 1;
        } else if (x < that.x) {
            return -1;
        } else if (x > that.x) {
            return 1;
        }
        return 0;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
        /* YOUR CODE HERE */
    }


    private class SlopeOrder implements Comparator<Point> {
        //here must change to comparing currente point slope to point a then currentpoint to point b

        public int compare(Point a, Point b) {
            return Double.compare(slopeTo(a), slopeTo(b));
        }
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
      /*
        Point[] l = new Point[5];
        l[0] = new Point(0, 0);
        System.out.println(l.length);
        System.out.println("----------------------");

        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.setPenRadius(0.005);
        StdDraw.enableDoubleBuffering();

        Point p = new Point(0, 0);
        Point p1 = new Point(5, 1);
        Point p2 = new Point(1, 2);
        Point p3 = new Point(0, 0);


        Point[] points = new Point[3];
        points[0] = p1;
        points[1] = p2;
        points[2] = p3;
        Arrays.sort(points);
        for (int i = 0; i < 3; i++) {
            System.out.println(points[i]);
        }
    */
    }
}
