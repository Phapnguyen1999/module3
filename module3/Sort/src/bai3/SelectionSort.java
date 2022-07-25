package bai3;

public class SelectionSort {
    static double[] list = {4, 1, 6.5, -1.4, 5.7, 8.9};

    public static void selectionSort(double[] list) {
        int leng = list.length;
        for (int i = 0; i < leng; i++) {
            double min = list[i];
            int index = i;
            for (int j = i + 1; j < leng; j++) {
                if (min > list[j]) {
                    min = list[j];
                    index = j;
                }
            }
            if (index != i) {
                list[index] = list[i];
                list[i] = min;
            }
        }
    }

    public static void main(String[] args) {
        selectionSort(list);
        System.out.print("[");
        for (int i = 0; i < list.length; i++) {
            System.out.print(list[i] + " ");
        }
        System.out.print("]");
    }
}
