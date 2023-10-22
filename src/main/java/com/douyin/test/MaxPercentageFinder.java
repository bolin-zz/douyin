package com.douyin.test;


import com.douyin.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaxPercentageFinder {
    public static void main(String[] args) {
        String input = "棉84%、聚酰胺纤维(锦纶)30%、聚氨酯弹性纤维(氨纶)66%";
        String[] result = StringUtils.getMaxPercentageAndText(input);


        System.out.println("Max Percentage Text: " + result[0]);
        System.out.println("Max Percentage: " + result[1] + "%");
    }
}
