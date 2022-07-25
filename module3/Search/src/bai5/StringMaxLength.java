package bai5;

import java.util.LinkedList;
import java.util.Scanner;

public class StringMaxLength {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        LinkedList<Character> max = new LinkedList<>();
        LinkedList<Character> list = new LinkedList<>();
        System.out.println("Nhập chuỗi");
        String string = input.nextLine();
        int leng = string.length();
        for (int i = 0; i < leng; i++)
        {
            if(list.size() > 1 && string.charAt(i) <= list.getLast()
                    && list.contains(string.charAt(i)))
            {
                list.clear();
            }
            list.add(string.charAt(i));
            if(list.size() > max.size())
            {
                max.clear();
                max.addAll(list);
            }
        }
        for (Character ch : max)
        {
            System.out.print(ch);
        }
        System.out.println();
    }
}
