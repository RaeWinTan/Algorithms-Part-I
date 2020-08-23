import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private int len;
    private KdNode root;
    private double closestSoFar;

    public KdTree() {
        len = 0;
        root = null;
    }

    private class KdNode {

        public Point2D p;
        public boolean POSITION;
        public KdNode left;
        public KdNode right;


        public KdNode(Point2D p, KdNode parent) {
            if (p == null) throw new IllegalArgumentException();
            left = null;
            right = null;
            //  this.parent = parent;
            this.p = p;
            if (parent != null) {
                this.POSITION = !parent.POSITION;

            } else {
                this.POSITION = true;

            }
        }

        public int com(Point2D t, boolean position) {
            if (position) {
                if (Double.compare(t.x(), p.x()) < 0) return -1;
                if (Double.compare(t.x(), p.x()) > 0) return 1;
            } else {
                if (Double.compare(t.y(), p.y()) < 0) return -1;
                if (Double.compare(t.y(), p.y()) > 0) return 1;
            }
            return 0;
        }
    }


    public boolean isEmpty() {
        return len == 0;
    }

    public int size() {
        return len;
    }

    public void insert(Point2D p) {
        if (contains(p)) {
            return;
        }
        if (isEmpty()) {
            root = new KdNode(p, null);
        } else {
            KdNode current = root;
            KdNode prev = null;
            boolean lr = false;
            while (current != null) {
                prev = current;
                if (current.com(p, current.POSITION) == -1) {
                    current = current.left;
                    lr = true;
                } else {
                    current = current.right;
                    lr = false;
                }
            }
            //the lr is to update the parent's left or right
            if (lr) {
                prev.left = new KdNode(p, prev);
            } else {
                prev.right = new KdNode(p, prev);
            }
        }
        len += 1;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        KdNode n = root;
        if (isEmpty()) return false;
        while (n != null) {
            if (n.p.equals(p)) return true;
            if (n.com(p, n.POSITION) == -1) n = n.left;
            else n = n.right;
        }
        return false;
    }

    public void draw() {
        KdNode current = root;
        addAllDraw(current, new RectHV(0, 0, 1, 1));
    }


    private void addAllDraw(KdNode node, RectHV r) {
        //split into two halves depending on the node.POSITION
        //draw the point then draw the line
        if (node == null) return;
        StdDraw.setPenRadius(0.001);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(node.p.x(), node.p.y());

        if (node.POSITION) {
            StdDraw.setPenRadius(0.001);
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.line(node.p.x(), r.ymin(), node.p.x(), r.ymax());
            if (node.left != null) {
                addAllDraw(node.left, new RectHV(r.xmin(), r.ymin(), node.p.x(), r.ymax()));
            }
            if (node.right != null) {
                addAllDraw(node.right, new RectHV(node.p.x(), r.ymin(), r.xmax(), r.ymax()));
            }
        } else {
            StdDraw.setPenRadius(0.001);
            StdDraw.setPenColor(StdDraw.PINK);
            StdDraw.line(r.xmin(), node.p.y(), r.xmax(), node.p.y());
            if (node.left != null) {
                addAllDraw(node.left, new RectHV(r.xmin(), r.ymin(), r.xmax(), node.p.y()));
            }
            if (node.right != null) {
                addAllDraw(node.right, new RectHV(r.xmin(), node.p.y(), r.xmax(), r.ymax()));
            }
        }

    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> l = new ArrayList<Point2D>();
        if (isEmpty()) return l;
        addAll(root, new RectHV(0, 0, 1, 1), l, rect);
        return l;

    }


    private void addAll(KdNode node, RectHV r, ArrayList<Point2D> l, RectHV range) {
        if (node == null) return;
        if (!range.intersects(r)) return;
        if (range.contains(node.p)) l.add(node.p);
        if (node.POSITION) {
            if (node.left != null) addAll(node.left, new RectHV(r.xmin(), r.ymin(), node.p.x(), r.ymax()), l, range);
            if (node.right != null) addAll(node.right, new RectHV(node.p.x(), r.ymin(), r.xmax(), r.ymax()), l, range);
        } else {
            if (node.left != null) addAll(node.left, new RectHV(r.xmin(), r.ymin(), r.xmax(), node.p.y()), l, range);
            if (node.right != null) addAll(node.right, new RectHV(r.xmin(), node.p.y(), r.xmax(), r.ymax()), l, range);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        KdNode cur = root;
        closestSoFar = 2;
        Point2D cl[] = new Point2D[1];
        nearestRecur(cur, new RectHV(0, 0, 1, 1), p, cl);
        return cl[0];
    }

    private void nearestRecur(KdNode node, RectHV r, Point2D p, Point2D[] cl) {
        if (r.distanceTo(p) > closestSoFar) return;
        if (node.p.distanceTo(p) < closestSoFar) {
            closestSoFar = node.p.distanceTo(p);
            cl[0] = node.p;
        }
        if (node.POSITION) {
            //now must check who towards the side of the query point
            if (p.x() < node.p.x()) {
                if (node.left != null)
                    nearestRecur(node.left, new RectHV(r.xmin(), r.ymin(), node.p.x(), r.ymax()), p, cl);
                if (node.right != null)
                    nearestRecur(node.right, new RectHV(node.p.x(), r.ymin(), r.xmax(), r.ymax()), p, cl);
            } else {
                if (node.right != null)
                    nearestRecur(node.right, new RectHV(node.p.x(), r.ymin(), r.xmax(), r.ymax()), p, cl);
                if (node.left != null)
                    nearestRecur(node.left, new RectHV(r.xmin(), r.ymin(), node.p.x(), r.ymax()), p, cl);
            }
        } else {
            if (p.y() < node.p.y()) {
                if (node.left != null)
                    nearestRecur(node.left, new RectHV(r.xmin(), r.ymin(), r.xmax(), node.p.y()), p, cl);
                if (node.right != null)
                    nearestRecur(node.right, new RectHV(r.xmin(), node.p.y(), r.xmax(), r.ymax()), p, cl);
            } else {
                if (node.right != null)
                    nearestRecur(node.right, new RectHV(r.xmin(), node.p.y(), r.xmax(), r.ymax()), p, cl);
                if (node.left != null)
                    nearestRecur(node.left, new RectHV(r.xmin(), r.ymin(), r.xmax(), node.p.y()), p, cl);
            }
        }
    }
}
