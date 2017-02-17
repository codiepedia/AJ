package Week2;

/**
 * Created by yafengwang on 9/18/16.
 */

import java.util.*;

/**
 * public class RandomizedQueue<Item> implements Iterable<Item> {
 public RandomizedQueue()                 // construct an empty randomized queue
 public boolean isEmpty()                 // is the queue empty?
 public int size()                        // return the number of items on the queue
 public void enqueue(Item item)           // add the item
 public Item dequeue()                    // remove and return a random item
 public Item sample()                     // return (but do not remove) a random item
 public Iterator<Item> iterator()         // return an independent iterator over items in random order
 public static void main(String[] args)   // unit testing
 }
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int size;

    public RandomizedQueue() {
        this.queue = (Item[]) new Object[1];
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void enqueue(Item item) {

        if(item == null) {
            throw new NullPointerException();
        }

        if(queue.length == size) {
            // Resize
            Item[] copy = (Item[]) new Object[queue.length * 2];
            for(int i=0; i < queue.length ; i++) {
                copy[i] = queue[i];
            }
            this.queue = copy;
        }

        queue[size] = item;
        size++;
    }

    public Item dequeue() {

        if(size == 0) {
            throw new NoSuchElementException();
        }

        // Min + (int)(Math.random() * ((Max - Min) + 1))
        int random_pos = (int)(Math.random() * size);

        // We do not care about the sequence in the array, so we can always replace the last element with current one
        // to remove an element
        Item result = queue[random_pos];
        queue[random_pos] = queue[size - 1];
        size--;

        // Shrink
        if(size < queue.length / 4) {
            Item[] copy = (Item[]) new Object[queue.length / 2];
            for(int i=0; i<size; i++) {
                copy[i] = queue[i];
            }
            queue = copy;
        }

        return result;
    }

    public Item sample() {

        if(size == 0) {
            throw new NoSuchElementException();
        }

        return queue[(int)(Math.random() * size)];
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private int[] order;
        private int current;

        ListIterator() {
            order = new int[size];
            for(int i=0; i < size ; i++) {
                order[i] = i;
            }

            shuffleFisherYates(order);

            current = 0;
        }

        private void shuffleFisherYates(int[] array) {
            int n = array.length;
            for (int i = 0; i < array.length; i++) {
                // Get a random index of the array past i.
                int random = i + (int) (Math.random() * (n - i));
                // Swap the random element with the present element.
                int randomElement = array[random];
                array[random] = array[i];
                array[i] = randomElement;
            }
        }

        @Override
        public boolean hasNext() {
            return current < order.length;
        }

        @Override
        public Item next() {

            if(! hasNext()) {
                throw new NoSuchElementException();
            }

            Item result = queue[order[current]];
            current ++;
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        // 1, 2, 3
        System.out.println(queue.isEmpty());
        System.out.println(queue.size());
        for(int x : queue) {
            System.out.print(x);
        }
        System.out.println();
        System.out.print(queue.dequeue());
        System.out.print(queue.dequeue());
    }
}
