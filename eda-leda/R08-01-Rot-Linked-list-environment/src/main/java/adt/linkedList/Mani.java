package adt.linkedList;

import java.util.Arrays;

public class Mani {
    public static void main(String[] args) {
        SingleLinkedListImpl<Integer> linkedList = new SingleLinkedListImpl<Integer>();
        linkedList.insert(new Integer(3));
        linkedList.insert(new Integer(3));
        linkedList.insert(new Integer(3));
        linkedList.insert(new Integer(3));

        System.out.println(Arrays.toString(linkedList.toArray()));
        //System.out.println(linkedList.size());
        //linkedList.toArray();
    }
}
