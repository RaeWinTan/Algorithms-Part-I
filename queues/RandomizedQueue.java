import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    //its an array implementation
    // construct an empty randomized queue
    private Item[] q;
    private int capacity;
    private int sz;

    public RandomizedQueue() {
        capacity = 0;
        sz = 0;
    }

    private int randomInt() {
        return StdRandom.uniform(sz);
    }

    private void enlarge(int cap) {
        Item[] copy = (Item[]) new Object[cap];
        for (int i = 0; i < sz; i++)
            copy[i] = q[i];
        q = copy;
        capacity = cap;
    }

    private void shrink(int cap) {
        Item[] copy = (Item[]) new Object[cap];
        for (int i = 0; i < sz; i++)
            copy[i] = q[i];
        q = copy;
        capacity = cap;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return sz == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return sz;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (sz == 0) {
            capacity++;
            enlarge(2 * capacity);
        } else if (sz == capacity) enlarge(2 * capacity);
        q[sz++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        //remove then replace by the last item in the array
        //if its the last one just delete the last guy directly
        if (sz == 0) throw new NoSuchElementException();
        int ran = randomInt();
        Item temp = q[ran];
        if (ran == sz - 1) {
            q[--sz] = null;
        } else {
            q[ran] = q[--sz];
            q[sz] = null;
        }
        //do the shrinking here
        if (capacity / 2 == sz) shrink(capacity / 2);
        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (sz == 0) throw new NoSuchElementException();
        return q[randomInt()];
    }

    // return an independent iterator over items in random order
    //use the StdRandom.permutation(size())
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private int[] arr;
        private int index;

        public QueueIterator() {
            index = 0;
            arr = new int[sz];
            arr = StdRandom.permutation(sz);
        }

        public boolean hasNext() {
            return index != sz;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            Item i = q[arr[index++]];
            return i;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(12);
        rq.enqueue(10);
        StdOut.println(rq.isEmpty());
        StdOut.println(rq.size());
        StdOut.println(rq.dequeue());
        StdOut.println(rq.sample());
        for (int i : rq)
            StdOut.println(i);
    }

}

