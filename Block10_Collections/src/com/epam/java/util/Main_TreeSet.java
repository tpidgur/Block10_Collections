package com.epam.java.util;

import java.util.Iterator;

/**
 * Created by Зая on 07.06.2016.
 */
public class Main_TreeSet {
    public static void main(String[] args) {
        MyTreeSet2 <Integer> tree=new MyTreeSet2();
        tree.getRoot().setValue(Integer.valueOf(7));
        tree.add(4);
        tree.add(5);
        tree.add(13);
        tree.add(12);
        tree.add(3);
      //  System.out.println(tree);


        System.out.println(tree.getRoot().toList());

        Iterator itr = tree.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next()+" ");
            itr.remove();

        }
        System.out.println(tree);
    }
}
