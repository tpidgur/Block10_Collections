package com.epam.java.util;

/**
 * @author Pidhurska Tetiana
 * @version June 3, 2016
 */
public class MainLinkedList {
    public static void main(String[] args) {
        OwnLinkedList<Integer> list = new OwnLinkedList();
        OwnLinkedList<Integer> list2 = new OwnLinkedList();
        System.out.print("Offerfirst: ");
        for (int i = 1; i < 6; i++) {
            list.offerFirst(i);
            list2.offerFirst(i);
        }
        list.print();

        System.out.print("OfferLast: ");
        for (int i = 5; i < 7; i++) {
            list.offerLast(i);
            list2.offerFirst(i);
        }
        list.print();

//        System.out.print("PollFirst: ");
//        for (int i = 0; i < 2; i++) {
//            list.pollFirst();
//        }
//        list.print();
//
//        System.out.print("PollLast: ");
//        for (int i = 0; i < 2; i++) {
//            list.pollLast();
//        }
//        list.print();
//        System.out.println("Size: " + list.size());

//        ListIterator<Integer> itr = list.iterator();
//        while (itr.hasNext()) {
//            itr.next();
//            itr.remove();
//        }
//        while (itr.hasPrevious()) {
//            itr.previous();
//            itr.remove();
//        }

      //list.removeFirstOccurrence(5);
       list.removeLastOccurrence(5);
        list.addLast(7);
        list.print();
       list2.print();


        System.out.println( list.containsAll(list2));
        System.out.println( list2.containsAll(list));

//        System.out.println("Size: " + list.size());
        System.out.println(list.retainAll(list2));
        System.out.println(list);

    }
}
