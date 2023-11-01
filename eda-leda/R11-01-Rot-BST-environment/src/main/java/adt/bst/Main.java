package adt.bst;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        BSTImpl<Integer> bst = new BSTImpl<Integer>();
        bst.insert(new Integer(10));
        bst.insert(new Integer(5));
        bst.insert(new Integer(7));
        bst.insert(new Integer(2));
        bst.insert(new Integer(6));
        bst.insert(new Integer(9));
        bst.insert(new Integer(77));
        bst.insert(new Integer(8));
        bst.insert(new Integer(22));



        System.out.println(Arrays.toString(bst.preOrder()));
    }
}
