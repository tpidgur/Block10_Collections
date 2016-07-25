package com.epam.java.util;

import java.util.*;

/**
 * Created by Зая on 04.06.2016.
 */
public class MyArrayList<T> implements List {
    public static final int INITAL_SIZE = 10;
    T[] array;
    int capacity = INITAL_SIZE;
    int size;//length of array


    public MyArrayList(int initialSize) {
        array = (T[]) new Object[initialSize];

    }

    public MyArrayList() {
        array = (T[]) new Object[INITAL_SIZE];
    }

    public void ensureCapacity(int estimatedSize) {
        if (estimatedSize > capacity) {
            capacity = (capacity * 3) / 2 + 1;
            T[] arrayNew = (T[]) new Object[capacity];
            System.arraycopy(array, 0, arrayNew, 0, array.length);
            array = arrayNew;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public boolean contains(Object o) {
        return (indexOf(o) != -1);
    }

    @Override
    public Iterator iterator() {
        return listIterator();
    }

//    private class SimpleIterator implements Iterator {
//        int index = -1;
//
//        @Override
//        public boolean hasNext() {
//            if (index < size - 1) {
//                return true;
//            }
//            return false;
//        }
//
//        @Override
//        public Object next() {
//            return array[++index];
//        }
//
//        @Override
//        public void remove() {
//            MyArrayList.this.remove(index--);
//
//
//        }
//    }
    @Override
    public ListIterator listIterator() {
        return listIterator(-1);
    }

    @Override
    public ListIterator listIterator(int startIndex) {
        return new ListIterator<T>() {
            int index = startIndex;

            @Override
            public boolean hasNext() {
                if (index < size-1) {
                    return true;
                }
                return false;
            }

            @Override
            public T next() {
                return array[++index];
            }

            @Override
            public boolean hasPrevious() {
                return index > 0;
            }

            @Override
            public T previous() {
                return (T) array[--index];
            }

            @Override
            public int nextIndex() {
                return  index + 1;
            }

            @Override
            public int previousIndex() {
                return  index - 1;
            }

            @Override
            public void remove() {
                MyArrayList.this.remove(index--);
            }

            @Override
            public void set(T o) {
                MyArrayList.this.set(index, o);

            }

            @Override
            public void add(T o) {
                MyArrayList.this.add(index, o);
            }

        };

    }
    @Override
    public Object[] toArray() {
        T[] trimmedArr = (T[]) new Object[size];
        System.arraycopy(array, 0, trimmedArr, 0, size);
        return trimmedArr;
    }

    @Override
    public boolean add(Object o) {
        ensureCapacity(size + 1);
        array[size++] = (T) o;//the element is added at the end of the list and current size is increased by one
        return true;
    }


    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(o)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        boolean isModified = false;
        Iterator itr = c.iterator();
        while (itr.hasNext()) {
            add(itr.next());
            isModified = true;
        }
        return isModified;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        boolean isModified = false;
        Iterator itr = c.iterator();
        while (itr.hasNext()) {
            add(index++, itr.next());
            isModified = true;
        }
        return isModified;
    }

    @Override
    public void clear() {
        //SimpleIterator itr = (SimpleIterator) iterator();
        Iterator itr = MyArrayList.this.iterator();
        while (itr.hasNext()) {
            itr.next();
            itr.remove();
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return array[index];
    }

    @Override
    public T set(int index, Object element) {
        T oldParameter = get(index);
        array[index] = (T) element;
        return oldParameter;
    }

    @Override
    public void add(int addIndex, Object element) {
        ensureCapacity(size + 1);
        System.arraycopy(array, addIndex, array, addIndex + 1, size - addIndex);
        array[addIndex] = (T) element;
        size++;
    }

    @Override
    public T remove(int index) {
        T removedElem = get(index);
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[--size] = null;
        return removedElem;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size(); i++) {
            if (array[i].equals(o)) {
                return i;
            }
//          if  (o==null ? get(i)==null : o.equals(get(i)));
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size() - 1; i >= 0; i--) {
            if (array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }



    @Override
    public List subList(int fromIndex, int toIndex) {
        MyArrayList subList = new MyArrayList();
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        } else if (fromIndex < 0 || toIndex > size) {
            throw new IndexOutOfBoundsException();
        } else {
            for (int i = 0; i < array.length; i++) {
                if (i >= fromIndex && i < toIndex) {
                    subList.add(array[i]);
                }
            }
        }
        return subList;
    }

    @Override
    public boolean retainAll(Collection c) {
        boolean isModified = false;
        Iterator itr = MyArrayList.this.iterator();
//        SimpleIterator itr = (SimpleIterator) iterator();
        while (itr.hasNext()) {
            if (!c.contains(itr.next())) {
                itr.remove();
                isModified = true;
            }
        }
        return isModified;
    }

    @Override
    public boolean removeAll(Collection c) {
        boolean isRemoved = false;
        Iterator itr = c.iterator();
        while (itr.hasNext()) {
            Object elem = itr.next();
            if (contains(elem)) {
                remove(elem);
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    @Override
    public boolean containsAll(Collection c) {
        Iterator itr = c.iterator();
        while (itr.hasNext()) {
            if (!contains(itr.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object[] toArray(Object[] a) {
        T[] trimmedArr = (T[]) new Object[size];
        System.arraycopy(array, 0, trimmedArr, 0, size);
        return trimmedArr;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator itr = MyArrayList.this.iterator();

//        SimpleIterator itr = (SimpleIterator) iterator();
        sb.append("[");
        while (itr.hasNext()) {
            sb.append(itr.next());
            if (itr.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");

        return sb.toString();
    }
}
