package bai2;

import java.util.LinkedList;
import java.util.Scanner;

public class Maxlength {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Nhập chuỗi");
        String string = input.nextLine();
        LinkedList<Character> max = new LinkedList<>();

        int leng = string.length();
        for (int i = 0; i < leng; i++) {
            LinkedList<Character> list = new LinkedList<>();
            list.add(string.charAt(i));
            for (int j = i + 1; j < leng; j++) {
                if (string.charAt(j) > list.getLast())
                    list.add(string.charAt(j));
            }
            if (list.size() > max.size()) {
                max.clear();
                max.addAll(list);
            }
        }
        for (Character character : max) {
            System.out.println(character);
        }
        System.out.println();
    }
}
