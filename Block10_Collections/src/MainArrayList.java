import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Зая on 04.06.2016.
 */
public class MainArrayList {
    public static void main(String[] args) {
        MyArrayList<Integer> arrayList = new MyArrayList<>();
        for (int i = 0; i < 16; i++) {
            arrayList.add(i);
        }
//        System.out.println(arrayList);
//        arrayList.add(5,100);
//        System.out.println("After addition 100: \n"+arrayList);
//
//        //System.out.println("After deletion:\n"+arrayList.remove(5));
//        System.out.println(arrayList);
//        System.out.println("LastIndexOf: "+arrayList.lastIndexOf(0));
//        System.out.println("Set: "+arrayList.set(5,100));
//        System.out.println(arrayList);
//        System.out.println("Remove(object)"+arrayList.remove( new Integer("10")));
//        System.out.println(arrayList);
//        System.out.println("Contains Object:" +arrayList.contains(10));

//        Iterator itr=arrayList.iterator();
//        System.out.print("Iterator: ");
//        while (itr.hasNext()){
//            System.out.print(itr.next()+"; ");
//            itr.remove();
//        }
//        System.out.println("___________");
//        System.out.println(arrayList);
//        System.out.println("___________");
        MyArrayList<Integer> arrayList2 = new MyArrayList<>();
        for (int i = 0; i <3; i++) {
            arrayList2.add(i);
        }
//      System.out.println("\nArrayList2: "+arrayList2);
//        arrayList2.clear();
  //  arrayList2.retainAll(arrayList);
        //System.out.println(arrayList2.containsAll(arrayList));
//        System.out.println(arrayList2.removeAll(arrayList));
//        System.out.println(arrayList2);
        System.out.println(arrayList2);
       System.out.println(Arrays.toString(arrayList2.toArray()));
       // arrayList.addAll(arrayList2);
       //arrayList.addAll(1,arrayList2);

        System.out.println(arrayList.subList(0,5));
    }
}
