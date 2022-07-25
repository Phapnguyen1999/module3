package bai2;

import java.util.Scanner;

public class BubbleSortByStep {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter list size: ");
        int size = sc.nextInt();
        int[] list = new int[size];
        System.out.println("Enter " + list.length + " values ");
        int leng = list.length;
        for (int i = 0; i < leng; i++) {
            list[i] = sc.nextInt();
        }
        System.out.println("Your input list: ");
        for (int i = 0; i < leng; i++) {
            System.out.printf(list[i] + "\t");
        }
        System.out.println("Begin sort processing");
        bubbleSortByStep(list);
    }

    public static void bubbleSortByStep(int[] list) {
        boolean needNextPass = true;
        int leng = list.length;
        for (int i = 1; i < leng; i++) {
            needNextPass = false;
            for (int j = 0; j < leng - i; j++) {
                if (list[j] > list[j + 1]) {
                    System.out.println("Swap " + list[j] + " with " + list[j + 1]);
                    int temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                    needNextPass = true;
                }
            }
            if ( needNextPass == false) {
                System.out.println("Array may be sorted and next pass not needed ");
                break;
            }
            System.out.println("List after the " + i + "sort ");
            for (int j = 0; j < leng; j++) {
                System.out.print(list[j] + "\t");
            }
            System.out.println();
        }

    }
}
