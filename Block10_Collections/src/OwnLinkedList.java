import java.util.*;

/**
 * Created by Зая on 05.06.2016.
 */
public class OwnLinkedList<E> implements Iterable<E>, Deque<E> {
    private Entry<E> header = new Entry<E>(null, null, null);
    private int size;
    private int modCount;

    public OwnLinkedList() {
        header.prev = header.next = header;//header has prev and next references watching at itself
    }

    private static class Entry<E> {
        E element;
        Entry<E> prev;
        Entry<E> next;

        Entry(E element, Entry<E> prev, Entry<E> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            if (element != null) {
                return element.toString();
            }
            return null;
        }
    }

    @Override
    public void addFirst(E e) {
        offerFirst(e);
    }

    @Override
    public void addLast(E e) {
        offerLast(e);
    }

    @Override
    public boolean offerFirst(E e) {//imagine that the newElem will be created between header and nextElem
        modCount++;
        Entry<E> newElem = new Entry<>(e, header, header.next);
        Entry<E> nextElem = header.next;
        newElem.prev = header;
        newElem.next = nextElem;
        nextElem.prev = newElem;
        header.next = newElem;
        size++;
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        modCount++;
        Entry<E> newElem = new Entry<>(e, header.prev, header);
        Entry<E> prevElem = header.prev;
//        newElem.next = header;
//        newElem.prev = prevElem;
        prevElem.next = newElem;
        header.prev = newElem;
        size++;
        return true;
    }

    @Override
    public E removeFirst() {
        if (header == header.next) {
            throw new NoSuchElementException("No Such Element Exception!");
        }
        return pollFirst();
    }

    @Override
    public E removeLast() {
        if (header == header.prev) {
            throw new NoSuchElementException("No Such Element Exception!");
        }
        return pollLast();
    }

    @Override
    public E pollFirst() {
        if (header == header.next) {
            return null;
        }
        modCount++;
        Entry<E> removedElem = header.next;
        Entry<E> nextAfterRemoved = header.next.next;
        header.next = nextAfterRemoved;
        nextAfterRemoved.prev = header;
        size--;
        return removedElem.element;
    }

    @Override
    public E pollLast() {
        if (header == header.prev) {
            return null;
        }
        modCount++;
        Entry<E> removedElem = header.prev;
        Entry<E> prepreLast = removedElem.prev;
        prepreLast.next = header;
        header.prev = prepreLast;
        size--;
        return removedElem.element;
    }

    @Override
    public E getFirst() {
        if (header == header.prev) {
            throw new NoSuchElementException("No Such Element Exception!");
        }
        return peekFirst();
    }

    @Override
    public E getLast() {
        if (header == header.prev) {
            throw new NoSuchElementException("No Such Element Exception!");
        }
        return peekLast();
    }

    @Override
    public E peekFirst() {
        if (header == header.next) {
            return null;
        }
        return header.next.element;
    }

    @Override
    public E peekLast() {
        if (header == header.prev) {
            return null;
        }
        return header.prev.element;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        MyListIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (iter.next().equals(o)) {
                iter.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        MyListIterator iter = this.iterator();
        while (iter.hasPrevious()) {
            if (iter.previous().equals(o)) {
                iter.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public boolean offer(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E poll() {
        return removeFirst();
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public void push(E e) {
        offerFirst(e);
    }

    @Override
    public E pop() {
        return pollFirst();
    }

    @Override
    public boolean remove(Object o) {
        while (contains(o)){
            removeFirstOccurrence(o);
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        MyListIterator itr = (MyListIterator) c.iterator();
        while (itr.hasNext()) {
            if (!contains(itr.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean isModified = false;
        MyListIterator itr = (MyListIterator) c.iterator();
        while (itr.hasNext()) {
            add(itr.next());
            isModified = true;
        }
        return isModified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isRemoved = false;
        MyListIterator itr = (MyListIterator) c.iterator();
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
    public boolean retainAll(Collection<?> c) {
        boolean isModified = false;
        MyListIterator itr = this.iterator();
        while (itr.hasNext()) {
            if (!c.contains(itr.next())) {
                itr.remove();
                isModified = true;
            }
        }
        return isModified;
    }

    @Override
    public void clear() {
        MyListIterator itr = this.iterator();
        while (itr.hasNext()) {
           itr.next();
            itr.remove();
        }
    }

    @Override
    public boolean contains(Object o) {
        MyListIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (iter.next().equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        Object[] arr =  new Object[size];
        MyListIterator itr = this.iterator();
        while (itr.hasNext()) {
            add(itr.next());
        }
        return arr;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T[] arr = (T[]) new Object[size];
        MyListIterator itr = this.iterator();
        while (itr.hasNext()) {
            add(itr.next());
        }
        return arr;
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
    public Iterator<E> descendingIterator() {
        return new DescendingIterator();
    }

    private class DescendingIterator implements Iterator {
        private final MyListIterator itr = new MyListIterator();

        @Override
        public boolean hasNext() {
            return itr.hasPrevious();
        }

        @Override
        public E next() {
            return itr.previous();
        }

        @Override
        public void remove() {
            itr.remove();
        }
    }

    private class MyListIterator implements ListIterator<E> {
        int modCount = 0;
        Entry<E> returned = header;
        int nextIndex = 0;
        int prevIndex = size;
        boolean forward = true;

        @Override
        public boolean hasNext() {
            //return (nextIndex <= size + 1);
            return returned.next != header;
        }

        @Override
        public E next() {
            nextIndex++;
            returned = returned.next;
            return returned.element;
        }

        @Override
        public boolean hasPrevious() {
            // return (prevIndex > 0);
            return returned.prev != header;
        }

        @Override
        public E previous() {
            forward = false;
            prevIndex--;
            returned = returned.prev;
            return returned.element;
        }

        @Override
        public int nextIndex() {
            if (forward) {
                return nextIndex + 1;
            }
            return prevIndex - 1;
        }

        @Override
        public int previousIndex() {
            if (forward) {
                return nextIndex - 1;
            }
            return prevIndex + 1;
        }

        @Override
        public void remove() {
            if (returned == null) {
                throw new IndexOutOfBoundsException("Just Returned is null");
            } else if (returned == header.next) {
                OwnLinkedList.this.removeFirst();
            } else if (returned == header.prev) {
                OwnLinkedList.this.removeLast();
            } else {
                returned.next.prev = returned.prev;
                returned.prev.next = returned.next;
                returned = null;
                size--;
            }
        }

        @Override
        public void set(E e) {
            if (returned != null) {
                checkModificationCount();
                modCount++;
                returned.element = e;
            } else {
                throw new IndexOutOfBoundsException("Just Returned is null");
            }
        }

        @Override
        public void add(E e) {
            checkModificationCount();
            modCount++;
            if (returned == null) {
                throw new IndexOutOfBoundsException("Just Returned is null");
            } else if (returned == header.next) {
                OwnLinkedList.this.addFirst(e);
            } else if (returned == header.prev) {
                OwnLinkedList.this.addLast(e);
            } else {
                Entry<E> elem = new Entry<>(e, returned, returned.next);
                returned.next.prev = elem;
                returned.prev.next = elem;
            }

        }

        private void checkModificationCount() {
            if (OwnLinkedList.this.modCount != modCount) throw new ConcurrentModificationException();
        }
    }

    @Override
    public MyListIterator iterator() {
        return new MyListIterator();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        MyListIterator itr = iterator();
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

    public void print() {
        Entry<E> temp = header;
        System.out.print("[");
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                System.out.print(temp.next);
            } else {
                System.out.print(temp.next + ", ");
            }
            temp = temp.next;
        }
        System.out.println("]");
    }

}
