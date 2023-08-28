package sorting.divideAndConquer;

public class Mani {
    public static void main(String[] args) {
        MergeSort<Integer> sorter = new MergeSort<Integer>();
        Integer[] exampleArray = new Integer[]{1,5,2,8,3,0,7,4,6,9};
        sorter.sort(exampleArray, 0, 9);

        for(Integer element : exampleArray) {
            System.out.println(element);
        }
    }
}
