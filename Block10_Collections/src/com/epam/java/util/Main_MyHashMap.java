package com.epam.java.util;

import java.util.HashMap;

/**
 * Created by Pidhurska Tetiana on 30.06.2016.
 */
public class Main_MyHashMap {
    public static void main(String[] args) {
        MyHashMap<Integer, String> myHashMap = new MyHashMap();
        myHashMap.put(0, "A");
        myHashMap.put(1, "B");
        myHashMap.put(2, "C");
        myHashMap.put(3, "D");
        myHashMap.put(4, "E");
        myHashMap.put(5, "F");
        myHashMap.put(6, "G");
        myHashMap.put(7, "H");
        myHashMap.put(8, "I");
        myHashMap.put(9, "J");
        myHashMap.put(10, "K");
        myHashMap.put(11, "L");
        myHashMap.put(12, "M");
       myHashMap.put(null,"EE");
        //System.out.println(myHashMap);
       myHashMap.remove(null,"EE");
        System.out.println(myHashMap);

        HashMap<Integer, String> map = new HashMap();
        map.put(0, "A");
        map.put(1, "B");
        map.put(null, "EE");
        //System.out.println(map);
        map.remove(0);
        System.out.println(map);
    }

}
