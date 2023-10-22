package com.douyin.test;

public class ChineseStringLengthChecker {
    public static void main(String[] args) {
        String input1 = "你好";
        String input2 = "Hello";
        String input3 = "你好，Hello";
        String input4 = "";

        System.out.println("Input 1: " + isChineseStringLengthGreaterThanOrEqualToTwo(input1));
        System.out.println("Input 2: " + isChineseStringLengthGreaterThanOrEqualToTwo(input2));
        System.out.println("Input 3: " + isChineseStringLengthGreaterThanOrEqualToTwo(input3));
        System.out.println("Input 4: " + isChineseStringLengthGreaterThanOrEqualToTwo(input4));
    }

    public static boolean isChineseStringLengthGreaterThanOrEqualToTwo(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        int chineseCharCount = 0;
        for (char c : input.toCharArray()) {
            if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
                chineseCharCount++;
            }
        }

        return chineseCharCount >= 2;
    }
}
