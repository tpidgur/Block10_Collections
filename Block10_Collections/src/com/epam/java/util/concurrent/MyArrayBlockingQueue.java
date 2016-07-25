package com.epam.java.util.concurrent;

import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Зая on 24.07.2016.
 */
public class MyArrayBlockingQueue<T> extends AbstractQueue<T> implements BlockingQueue<T> {

    int putIndex;
    int takeIndex;


    final Object[] array;

    MyArrayBlockingQueue(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException();
        }
        array = new Object[capacity];

    }


    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public int size() {
        return getSize();
    }

    /**
     * Inserts the specified element at the tail of this queue if it is possible to do so immediately without exceeding
     * the queue's capacity
     *
     * @param t - element to be inserted
     * @return true upon success and false if this queue is full.
     */
    @Override
    public synchronized boolean offer(T t) {
        System.out.println("[ArrayBlockingQueue] try to offer" + t);
        if ((getSize() == array.length - 1)) {
            System.out.println("[ArrayBlockingQueue] is full. Unable to offer" + t);
            return false;
        } else {
            array[putIndex] = t;
            if (putIndex + 1 == array.length) {
                putIndex = 0;
            } else {
                putIndex++;
            }
            System.out.println("[ArrayBlockingQueue] has put successfully element " + t + " [ArrayBlockingQueue]" + Arrays.toString(array) + "nextPutIndex: " + putIndex);
            return true;
        }
    }

    /**
     * @return int number of elements in the array
     */
    public synchronized int getSize() {
        int currentSize = 0;

        if (putIndex - takeIndex < 0) {
            currentSize = array.length + putIndex - takeIndex;
            System.out.println("Getsize=" + currentSize);
            return currentSize;
        } else
            currentSize = ((putIndex - takeIndex) % (array.length));
        System.out.println("Getsize=" + currentSize);
        return currentSize;
    }

    /**
     * Inserts the specified element at the tail of this queue, waiting for space to become available if the queue is full.
     *
     * @param t
     * @throws InterruptedException
     */
    @Override
    public synchronized void put(T t) throws InterruptedException {
        checkNotNull(t);
        System.out.println("[ArrayBlockingQueue] try to put:" + t);
        while (getSize() == array.length - 1) {                 //коли черга повна
            System.out.println("[ArrayBlockingQueue] is full.Waiting for free space");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (getSize() == 0) {//якщо черга пуста
            System.out.println("[ArrayBlockingQueue] is empty, notify");
            notifyAll();
        }
        array[putIndex] = t;
        if (putIndex + 1 == array.length) {
            putIndex = 0;
        } else {
            putIndex++;
        }
        System.out.println("[ArrayBlockingQueue] has put successfully element " + t + " [ArrayBlockingQueue]" + Arrays.toString(array) + "nextPutIndex: " + putIndex);

    }

    @Override
    public boolean offer(T t, long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    /**
     * Retrieves and removes the head of this queue, waiting if necessary until an element becomes available.
     *
     * @return the removed element
     * @throws InterruptedException
     */
    @Override
    public synchronized T take() throws InterruptedException {
        System.out.println("[ArrayBlockingQueue] try to take element:" + array[takeIndex]);
        while (getSize() == 0) {                 //коли черга пуста
            System.out.println("[ArrayBlockingQueue] is empty.Waiting for new elems");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (getSize() == array.length - 1) {//якщо черга  повна
            System.out.println("[ArrayBlockingQueue] is full, notify");
            notify();
        }
        T temp = (T) array[takeIndex];
        array[takeIndex] = null;
        if (takeIndex + 1 == array.length) {
            takeIndex = 0;
        } else {
            takeIndex++;
        }
        System.out.println("[ArrayBlockingQueue] has taken successfully element " + temp + " [ArrayBlockingQueue]" + Arrays.toString(array) + "nextTakeIndex: " + takeIndex);
        return temp;
    }

    @Override
    public T poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    /**
     * Returns the number of additional elements that this queue can ideally
     * (in the absence of memory or resource constraints) accept without blocking.
     * @return
     */
    @Override
    public int remainingCapacity() {
        return array.length-1-getSize();
    }

    @Override
    public int drainTo(Collection<? super T> c) {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super T> c, int maxElements) {
        return 0;
    }

    /**
     * Retrieves and removes the head of this queue, or returns null if this queue is empty.
     *
     * @return the removed element or null
     */
    @Override
    public synchronized T poll() {
        System.out.println("[ArrayBlockingQueue] try to poll element:" + array[takeIndex]);
        if (getSize() == 0) {                 //коли черга пуста
            System.out.println("[ArrayBlockingQueue] is empty. Unable to poll");
            return null;
        } else {
            T temp = (T) array[takeIndex];
            array[takeIndex] = null;
            if (takeIndex + 1 == array.length) {
                takeIndex = 0;
            } else {
                takeIndex++;
            }
            System.out.println("[ArrayBlockingQueue] has polled successfully element " + temp + " [ArrayBlockingQueue]"
                    + Arrays.toString(array) + "nextTakeIndex: " + takeIndex);
            return temp;
        }
    }

    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     *
     * @return element of the queue
     */
    @Override
    public synchronized T peek() {
        if (getSize() == 0) {                 //коли черга пуста
            System.out.println("[ArrayBlockingQueue] is empty. Unable to peek");
            return null;
        } else {
            int peekIndex = takeIndex;
            System.out.println("PeekIndex: "+peekIndex);
            T peekedElem = (T) array[peekIndex];
            System.out.println("[ArrayBlockingQueue]  peeked head element of the queue :" + peekedElem);
            return peekedElem;
        }
    }

    private static void checkNotNull(Object object) {
        if (object == null)
            throw new NullPointerException();
    }
}
