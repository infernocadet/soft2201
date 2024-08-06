package org.example;

import java.util.Arrays;

public class Reverse {
    public static void reverse(int[] array) {
        int arrayLength = array.length;
        for (int i = 0; i < arrayLength / 2; i++) {
            int temp = array[i];
            array[i] = array[arrayLength - i - 1];
            array[arrayLength - i - 1] = temp;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4};
        reverse(arr);
        int[] newArr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println(Arrays.toString(arr));
        reverse(newArr);
        System.out.println(Arrays.toString(newArr));
    }
}
