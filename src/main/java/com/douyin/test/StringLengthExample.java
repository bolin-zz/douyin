package com.douyin.test;

import java.text.BreakIterator;

public class StringLengthExample {
    public static int getCharacterCount(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }

        int charCount = 0;
        for (char c : str.toCharArray()) {
            if (isChineseCharacter(c)) {
                charCount += 2;
            } else {
                charCount++;
            }
        }

        return charCount;
    }

    public static boolean isChineseCharacter(char c) {
        // 根据Unicode编码范围判断是否是中文字符
        return c >= 0x4E00 && c <= 0x9FFF;
    }

    public static void main(String[] args) {
        String text = "H你好世界来日方长";
        int charCount = getCharacterCount(text);
        System.out.println("Character Count: " + charCount);
    }
}
