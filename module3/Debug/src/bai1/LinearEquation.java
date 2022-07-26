package bai1;

import java.util.Scanner;

public class LinearEquation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập a");
        int a = scanner.nextInt();

        System.out.println("Nhập b");
        int b = scanner.nextInt();

        giaiPhuongTrinhBacNhat(a, b);
    }
    public static void giaiPhuongTrinhBacNhat(int a, int b) {
        if ( a != 0) {
            System.out.println("Phương trình có nghiệm " + (-b/a));
        } else if (b == 0) {
            System.out.println("Phương trình vô số nghiệm");
        } else {
            System.out.println("Phương trình vô nghiệm");
        }
    }
}
