package Week2;

/**
 * Created by yafengwang on 9/18/16.
 */

import java.util.Iterator;

/**
 * public class Deque<Item> implements Iterable<Item> {
 public Deque()                           // construct an empty deque
 public boolean isEmpty()                 // is the deque empty?
 public int size()                        // return the number of items on the deque
 public void addFirst(Item item)          // add the item to the front
 public void addLast(Item item)           // add the item to the end
 public Item removeFirst()                // remove and return the item from the front
 public Item removeLast()                 // remove and return the item from the end
 public Iterator<Item> iterator()         // return an iterator over items in order from front to end
 public static void main(String[] args)   // unit testing
 }

 */
public class Deque<Item> implements Iterable<Item> {

    private Node dummyHead;
    private Node dummyTail;
    private int size;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        Node(Item item) {
            this.item = item;
        }
    }

    public Deque() {
        dummyHead = new Node(null);
        dummyTail = new Node(null);
        dummyHead.next = dummyTail;
        dummyTail.prev = dummyHead;
        size = 0;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = dummyHead.next;

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public Item next() {

            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Item result = current.item;
            current = current.next;
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void addFirst(Item item) {

        if (item == null) {
            throw new NullPointerException();
        }

        Node newNode = new Node(item);
        Node oldNode = dummyHead.next;
        dummyHead.next = newNode;
        newNode.prev = dummyHead;
        newNode.next = oldNode;
        oldNode.prev = newNode;
        this.size++;
    }

    public void addLast(Item item) {

        if (item == null) {
            throw new NullPointerException();
        }

        Node newNode = new Node(item);
        Node oldNode = dummyTail.prev;
        oldNode.next = newNode;
        newNode.prev = oldNode;
        newNode.next = dummyTail;
        dummyTail.prev = newNode;
        this.size++;
    }

    public Item removeFirst() {
        if (this.size == 0) {
            throw new java.util.NoSuchElementException();
        }

        Node firstNode = dummyHead.next;
        Node secondNode = firstNode.next;

        dummyHead.next = secondNode;
        secondNode.prev = firstNode;

        this.size--;
        return firstNode.item;
    }

    public Item removeLast() {
        if (this.size == 0) {
            throw new java.util.NoSuchElementException();
        }

        Node lastNode = dummyTail.prev;
        Node secondLastNode = lastNode.prev;

        secondLastNode.next = dummyTail;
        dummyTail.prev = secondLastNode;

        this.size--;
        return lastNode.item;
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(3);
        // 3, 1, 2
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());
        for (int i : deque) {
            System.out.print(i);
        }
        System.out.println(deque.removeLast());
        System.out.println(deque.removeFirst());

        Deque<Integer> deque1 = new Deque<Integer>();
        deque.isEmpty();
        deque.isEmpty();
        deque.addLast(2);
        deque.isEmpty();
        deque.removeFirst();  //   ==> 2
        deque.addLast(5);
        deque.addLast(6);
        deque.removeFirst();
    }
}
