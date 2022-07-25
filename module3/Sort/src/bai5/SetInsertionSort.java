package bai5;

public class SetInsertionSort {
    public static double[] numbers = {3.4, 4.5, 2.5, 8.9, 7.8, 12.2, 23.2, 11.8};

    public static void insertionSort(double[] numbers) {
        int pos;
        double x;
        int leng = numbers.length;
        for (int i = 1; i < leng; i++) {
            pos = i;
            x = numbers[i];
            while (pos > 0 && x < numbers[pos - 1]) {
                numbers[pos] = numbers[pos - 1];
                pos--;
            }
            numbers[pos] = x;
        }
    }

    public static void main(String[] args) {
        insertionSort(numbers);
        System.out.println("After sertion Sort ");
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + " ");
        }
    }
}
