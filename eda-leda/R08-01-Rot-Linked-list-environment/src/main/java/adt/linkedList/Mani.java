package adt.linkedList;

import java.util.Arrays;

public class Mani {
    public static void main(String[] args) {
        //SingleLinkedListImpl<Integer> linkedList = new SingleLinkedListImpl<Integer>();
        DoubleLinkedListImpl<Integer> linkedList = new DoubleLinkedListImpl<Integer>();


        //System.out.println(linkedList.size());
        //linkedList.toArray();

/*         linkedList.insertFirst(1);
        linkedList.insertFirst(2);
        linkedList.insertFirst(3); */

        linkedList.insert(1);
        linkedList.insert(2);
        linkedList.insert(3);
        System.out.println(Arrays.toString(linkedList.toArray()));
    }
}
