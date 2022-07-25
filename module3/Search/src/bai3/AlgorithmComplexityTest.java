package bai3;

import java.util.Scanner;

public class AlgorithmComplexityTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a string");
        String inputString = sc.nextLine();
        int[] frequentChar = new int[255];
        int leng = inputString.length();
        for(int i = 0 ; i < leng; i++)
        {
            int ascii = inputString.charAt(i);
            frequentChar[ascii] += 1;
        }
        int max = 0;
        char character = (char) 255;
        for (int j = 0; j < 255; j++)
        {
            if (frequentChar[j] > max)
            {
                max = frequentChar[j];
                character = (char) j;
            }
        }
        System.out.println("The most apperaring letter is " + character + " with a frequency of "
                + max + "times ");
    }
}
