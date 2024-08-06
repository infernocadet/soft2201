package org.example;

import java.util.List;
import java.util.Collections;
import java.util.Arrays;


public class ReverseSort {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList(new String[]{
                "One",
                "Two",
                "Three",
                "Four",
                "Five",
                "Six",
                "Seven",
        });

        Collections.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });

        for (String s : strings) {
            System.out.println(s);
        }
    }
}
