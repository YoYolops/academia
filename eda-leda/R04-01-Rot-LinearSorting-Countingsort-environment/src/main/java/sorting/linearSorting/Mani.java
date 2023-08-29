package sorting.linearSorting;

public class Mani {
    public static void main(String[] args) {
        CountingSort sorter = new CountingSort();
        Integer[] exampleArray = new Integer[]{0,0,6,4,0,12,44, 44,12,9,1,0,7,2,5,1,6,9,7,1,2};

        sorter.sort(exampleArray, 0, 7);
    }
}
