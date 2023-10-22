package com.douyin.test;

import java.util.Arrays;
import java.util.List;

public class PercentageRangeIdentifier {
    public static int identifyPercentageRange(double percentage) {
        if (percentage <= 29) {
            return 0;
        } else if (percentage >= 95) {
            return 1;
        } else if (percentage >= 29 && percentage <= 39) {
            return 2;
        } else if (percentage >= 39 && percentage <= 49) {
            return 3;
        } else if (percentage >= 49 && percentage <= 59) {
            return 4;
        } else if (percentage >= 59 && percentage <= 69) {
            return 5;
        } else if (percentage >= 69 && percentage <= 79) {
            return 6;
        } else if (percentage >= 79 && percentage <= 89) {
            return 7;
        } else {
            return 8;
        }
    }

    public static void main(String[] args) {
        double percentage = 53.4;
        int index = identifyPercentageRange(percentage);
        System.out.println("百分数 " + percentage + "% 所属排序序列号：" + index);
    }
}
