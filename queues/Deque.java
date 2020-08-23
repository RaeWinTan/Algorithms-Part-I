import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    // construct an empty deque
    private int sz;
    private Node first;
    private Node last;
    private Node prev;

    public Deque() {
        sz = 0;
        first = null;
        last = null;
    }

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    public boolean isEmpty() {
        return sz == 0;
    }

    public int size() {
        return sz;
    }

    private boolean initialAdd(Item item) {
        if (size() == 0) {
            Node newNode = new Node();
            newNode.item = item;
            newNode.next = null;
            newNode.previous = null;
            first = newNode;
            last = newNode;
            sz++;
            return true;
        }
        return false;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (initialAdd(item)) return;
        Node oldFirst = first;
        first = new Node();
        oldFirst.previous = first;

        first.item = item;
        first.next = oldFirst;
        first.previous = null;

        sz++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (initialAdd(item)) return;

        last.next = new Node();
        last.next.item = item;

        last.next.next = null;
        last.next.previous = last;

        last = last.next;
        sz++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Node oldFirst = first;
        if (size() == 1) {
            last = null;
            first = null;
            sz--;
            return oldFirst.item;
        }
        first = first.next;
        sz--;
        return oldFirst.item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        //System.out.println(last.item);
        Node oldLast = last;
        if (size() == 1) {
            last = null;
            first = null;
            sz--;
            return oldLast.item;
        }
        last.previous.next = null;
        last = last.previous;
        sz--;
        return oldLast.item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<Integer>();
        StdOut.println(dq.isEmpty());
        StdOut.println(dq.size());
        dq.addFirst(5);
        dq.addLast(3);
        StdOut.println(dq.removeFirst());
        StdOut.println(dq.removeLast());
        StdOut.println(dq.size());
        for (int i : dq)
            StdOut.println(i);
    }

}
