package bai2;

import java.util.Scanner;

public class ArrayInteger {
    public Integer[] createRandom() {
        Random rd = new Random();
        Integer[] arrNumber = new Integer[100];
        for (int i = 0; i < 100; i++) {
            arrNumber[i] = rd.nextInt(i);
            System.out.print(arrNumber[i] + " " +
                    "");
        }
        return arrNumber;
    }

    private class Random {
        public Integer nextInt(int i) {
            return i = (int) Math.floor(Math.random() * 100 + 1);
        }
    }
    public static void main(String[] args) {
        ArrayInteger arrExample = new ArrayInteger();
        Integer[] arr = arrExample.createRandom();

        Scanner scaner = new Scanner(System.in);
        System.out.println("\nVui lòng nhập chỉ số của một phần tử bất kỳ: ");
        int x = scaner.nextInt();
        try {
            System.out.println("Giá trị của phần tử có chỉ số " + x + " là " + arr[x]);
        } catch (Exception ex ) {
            ex.printStackTrace();
            System.out.println("Chỉ số vượt quá mảng");
        }
    }
}
