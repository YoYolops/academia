package adt.heap;

import java.util.Comparator;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Heap<Integer> heap = new HeapImpl<Integer>(new ComparatorMaxHeap<Integer>());
        
		heap.insert(22);
		heap.insert(45);
		heap.insert(38);
		heap.insert(17);
		heap.insert(40);
		heap.insert(15);
		heap.insert(26);
		heap.insert(79);
        heap.insert(9);
		heap.insert(53);
		heap.insert(30);
        heap.insert(12);


        System.out.println("Extracted element: " + heap.extractRootElement());
        System.out.println("Extracted element: " + heap.extractRootElement());
        System.out.println("Extracted element: " + heap.extractRootElement());
        System.out.println("Extracted element: " + heap.extractRootElement());
        System.out.println("Extracted element: " + heap.extractRootElement());
        System.out.println("Extracted element: " + heap.extractRootElement());
        System.out.println("Extracted element: " + heap.extractRootElement());
        System.out.println("Extracted element: " + heap.extractRootElement());
        System.out.println("Extracted element: " + heap.extractRootElement());
        System.out.println("Extracted element: " + heap.extractRootElement());

        

        System.out.println(Arrays.toString(heap.toArray()));
        //System.out.println(Arrays.toString(heap.heapsort(new Integer[] { 34, 92, 5, 12, 49, 20, 43, 6 })));
    }
}
