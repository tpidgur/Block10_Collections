package com.epam.java.util.concurrent;

import java.util.Random;

/**
 * Created by Зая on 24.07.2016.
 */
public class MyArrayBlockingQueueMain {
    public static void main(String[] args) throws InterruptedException {
        MyArrayBlockingQueue queue = new MyArrayBlockingQueue(5);
        new Thread(new Producer(queue)).start();
        Thread.currentThread().sleep(1000);
        new Thread(new Consumer(queue)).start();
    }

    static class Producer implements Runnable {

        private final MyArrayBlockingQueue<Integer> queue;

        public Producer(MyArrayBlockingQueue<Integer> queue) {
            this.queue = queue;
        }


        @Override
        public void run() {
            System.out.println("[Producer] run");
            while (true) {
                try {
                    //  queue.put(produce());
                    queue.offer(produce());
                    queue.peek();
                    Thread.currentThread().sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private Integer produce() {
            Integer i = new Random().nextInt(100);
            System.out.println("[Producer] produce: " + i);
            return i;
        }
    }

    static class Consumer implements Runnable {
        private final MyArrayBlockingQueue<Integer> queue;

        public Consumer(MyArrayBlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            System.out.println("[Consumer] run");
            while (true) {
                try {
                    consume();
                    Thread.currentThread().sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void consume() throws InterruptedException {
            // Integer i = queue.take();
            Integer i = queue.poll();
            System.out.println("[Consumer] consumed: " + i);
        }
    }
}
