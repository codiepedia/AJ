package Week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;

/**
 * Created by yafengwang on 9/19/16.
 */
public class Subset {

    public static void main(String[] args) {

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        while(true) {
            try {
                randomizedQueue.enqueue(StdIn.readString());
            } catch (NoSuchElementException e) {
                break;
            }
        }

        int k = Integer.parseInt(args[0]);

        Iterator<String> i = randomizedQueue.iterator();
        int count = 0;
        while(count < k && i.hasNext()) {
            System.out.println(i.next());
            count++;
        }

    }

}
