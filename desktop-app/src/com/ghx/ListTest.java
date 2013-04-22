package com.ghx;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

class Producer implements Runnable {

    private List list;

    public Producer(List pList) {
        list = pList;
    }

    public void run() {
        System.out.println("Producer started");
        for (int i = 0; i < 5000; i++) {
            list.add(Integer.toString(i));
        }
        System.out.println("Producer completed " + list.size());
    }

}


class Consumer implements Runnable {
    private List list;

    public Consumer(List pList) {
        list = pList;
    }

    public void run() {
        System.out.println("Consumer started");
        for (int i = 0; i < 5000; i++) {
            while (!list.remove(Integer.toString(i))) {
                // Just iterating till an element is removed
            }

        }
        System.out.println("Consumer completed " + list.size());
    }
}


public class ListTest {

    public static void main(String[] args) throws InterruptedException {
//           List list = new Vector();
        List list = new ArrayList();

        for (int i = 0; i < 10; i++) {
            Thread p1 = new Thread(new Producer(list));
            p1.start();
        }

//        for (int i = 0; i < 10; i++) {
//            Thread c1 = new Thread(new Consumer(list));
//            c1.start();
//        }
        Thread.yield();

        while (Thread.activeCount() > 1) {
            Thread.sleep(100);
        }

        System.out.println(list.size());

    }

}
