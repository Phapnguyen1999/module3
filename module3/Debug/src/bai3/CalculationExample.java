package bai3;

import java.util.Scanner;

public class CalculationExample {
    public void cong(int a, int b) {
        System.out.println("tổng : " + (a + b));
    }

    public void tru(int a, int b) {
        System.out.println("Hiệu : " + (a - b));
    }

    public void nhan(int a, int b) {
        System.out.println("nhân : " + (a * b));
    }
    public void chia(int a, int b) {
        System.out.println("thương : " + (a / b));
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CalculationExample cal = new CalculationExample();
        System.out.println("Nhập a, b");
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        try {
            cal.cong(a, b);
            cal.tru(a, b);
            cal.nhan(a, b);
            cal.chia(a, b);

        } catch (Exception ex) {
            System.out.println("Lỗi! Nhập lại" + ex.getMessage());
        }
    }
}
