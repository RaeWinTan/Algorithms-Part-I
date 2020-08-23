import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> s;

    public PointSET() {
        s = new SET<Point2D>();
    }                 // construct an empty set of points

    public boolean isEmpty() {
        return s.isEmpty();
    }                      // is the set empty?

    public int size() {
        return s.size();
    }                         // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        s.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return s.contains(p);
    }            // does the set contain point p?

    public void draw() {

        for (Point2D p : s) {
            p.draw();
        }
    }                         // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> l = new ArrayList<Point2D>();
        for (Point2D p : s) {
            if (rect.contains(p)) l.add(p);
        }
        return l;

    }             // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Point2D nearestPoint = null;
        if (s.size() == 1) return p;
        for (Point2D z : s) {
            if (nearestPoint == null) nearestPoint = z;
            if (p.equals(z)) continue;
            if (p.distanceTo(z) < p.distanceTo(nearestPoint)) nearestPoint = z;
        }
        return nearestPoint;
    }             // a nearest neighbor in the set to point p; null if the set is empty


}
