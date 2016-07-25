package com.epam.java.util;

import java.util.*;

/**
 * Created by Pidhurska Tetiana on 30.06.2016.
 */
public class MyHashMap<K, V> implements Map {
    static final int MAXIMUM_CAPACITY = 1 << 30;
    /**
     * MyEntry array where the values are placed
     */
    private MyEntry[] basketTable;
    /**
     * number of elements in collection
     */
    private int size;
    /**
     * length of the basketTable
     */
    private int capacity = 16;
    /**
     * coefficient of the collection fullfillment
     */
    private float loadFactor = 0.75f;
    /**
     * parameter that defines when the basketTable length should be increased
     */
    private float threshold = capacity * loadFactor;

    /**
     * default constructor that builds basketTable with initial capacity=16
     */
    public MyHashMap() {
        basketTable = new MyEntry[capacity];
    }

    /**
     * constructor that builds basketTable with user defined capacity
     *
     * @param capacity
     */
    public MyHashMap(int capacity) {
        this.capacity = capacity;
        basketTable = new MyEntry[capacity];
    }

    /**
     * constructor that builds basketTable with user defined capacity and loadfactor
     *
     * @param capacity
     * @param loadFactor
     */
    public MyHashMap(int capacity, float loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        basketTable = new MyEntry[capacity];
    }

    /**
     * calculates hashcode
     *
     * @param h - corresponds to the default hashcode,i.e. object.hashcode()
     * @return int value of hashcode
     */
    static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * defines index of the basket where to place the value
     *
     * @param h      - hashcode of the value
     * @param length -length of the basketTable
     * @return int value of the basket
     */
    static int indexFor(int h, int length) {
        return h & (length - 1);
    }

    @Override
    public int size() {
        return getSize();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        return null;
    }

    @Override
    public Object put(Object key, Object value) {
        if (size + 1 > threshold) {
            resize(capacity * 2);
        }
        V oldValue = null;
        if (key == null) {
            putForNullKey(value);
            return oldValue;
        }
        int newHash = hash(key.hashCode());
        int basketNumber = indexFor(newHash, capacity);
        MyEntry currentEntry = basketTable[basketNumber];
        if (currentEntry == null) {
            addEntry(newHash, (K) key, (V) value, basketNumber);
        } else {
            while (currentEntry != null) {
                if (currentEntry.hash == newHash && (currentEntry.key == key || currentEntry.getKey().equals(key))) {
                    oldValue = (V) currentEntry.setValue(value);
                } else {
                    addEntry(newHash, (K) key, (V) value, basketNumber);
                }
                currentEntry = currentEntry.next;
            }
        }
        return oldValue;
    }

    public void addEntry(int hash, K key, V value, int index) {
        MyEntry<K, V> e = basketTable[index];
        basketTable[index] = new MyEntry<K, V>(hash, key, value, e);
        size++;
    }

    public void putForNullKey(Object value) {
        MyEntry current = basketTable[0];
        while (current != null) {
            if (current.getKey() == null) {
                current.setValue(value);
                break;
            }
            current = current.next;
        }
        addEntry(0, null, (V) value, 0);
    }

    @Override
    public Object remove(Object key) {
        V oldValue = null;
        MyEntry previous = null;
        int basketNumber;
        int elemHash;
        if (key == null) {
            basketNumber = 0;
            elemHash = 0;
        } else {
            elemHash = hash(key.hashCode());
            basketNumber = indexFor(elemHash, capacity);
        }
        MyEntry current = basketTable[basketNumber];
        while (current != null) {
            if (current.getKey() == null) {
                if (key == null) {
                    oldValue = (V) current.value;
                    size--;
                    if (previous == null) {
                        basketTable[basketNumber] = current.next;
                        break;
                    }
                    previous.next = current.next;
                    break;
                }
            } else if (current.hash == elemHash && (current.getKey() == key || current.getKey().equals(key))) {
                oldValue = (V) current.value;
                size--;
                if (previous == null) {
                    basketTable[basketNumber] = current.next;
                    break;
                }
                previous.next = current.next;
                break;
            }
            previous = current;
            current = current.next;
        }
        return oldValue;
    }

    public boolean remove(Object key, Object value) {
        V oldValue = null;
        MyEntry previous = null;
        int basketNumber;
        int elemHash;
        if (key == null) {
            basketNumber = 0;
            elemHash = 0;
        } else {
            elemHash = hash(key.hashCode());
            basketNumber = indexFor(elemHash, capacity);
        }
        MyEntry current = basketTable[basketNumber];
        while (current != null) {
            if (current.getKey() == null) {
                if (key == null && current.getValue().equals(value)) {
                    oldValue = (V) current.value;
                    size--;
                    if (previous == null) {
                        basketTable[basketNumber] = current.next;
                        break;
                    }
                    previous.next = current.next;
                    break;
                }
            } else if (current.hash == elemHash && (current.getKey() == key || current.getKey().equals(key)) && current.getValue().equals(value)) {
                oldValue = (V) current.value;
                size--;
                if (previous == null) {
                    basketTable[basketNumber] = current.next;
                    break;
                }
                previous.next = current.next;
                break;
            }
            previous = current;
            current = current.next;
        }

        return true;
    }

    @Override
    public void putAll(Map m) {
    }

    public void resize(int newCapacity) {
        if (basketTable.length == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        threshold = (int) (newCapacity * loadFactor);
        MyEntry[] newTable = new MyEntry[newCapacity];
        transfer(newTable);
        //basketTable=newTable;

    }

    public void transfer(MyEntry[] newTable) {
        MyEntry[] old = basketTable;
        basketTable = newTable;
        for (Entry element : old) {
            if (element == null) {
                continue;
            }
            int newHash = hash(element.hashCode());
            int basketNumber = indexFor(newHash, newTable.length);
            put(element.getKey(), element.getValue());
        }

    }

    @Override
    public void clear() {

    }

    @Override
    public Set keySet() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Set<Entry> entrySet() {
        return null;
    }

    static class MyEntry<K, V> implements Map.Entry<K, V> {
        final K key;
        V value;
        MyEntry<K, V> next;
        final int hash;


        public MyEntry(int hash, K key, V value, MyEntry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.hash = hash;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public String toString() {
            if (this.next == null) {
                return key +
                        "=" + value;
            } else {
                return next.toString() + ", " + key +
                        "=" + value;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (MyEntry elements : basketTable) {
            if (elements != null) {
                sb.append(elements.toString() + ", ");
            }
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.deleteCharAt(sb.lastIndexOf(" "));
        sb.append("}");
        return sb.toString();
    }
}
