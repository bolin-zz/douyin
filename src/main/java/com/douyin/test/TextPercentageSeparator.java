package com.douyin.test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextPercentageSeparator {
    public static void main(String[] args) {
        String input = "聚酯纤维100%%";
//        String input = "其他材质100%";
//        String input = "聚酯纤维96.5%、聚氨酯弹性纤维(氨纶)3.5%";
//        String input = "聚酯纤维93%、聚氨酯弹性纤维(氨纶)7%%";
//        String input = "棉100%";
//        String input = "棉51.4%、聚酯纤维46.9%、聚氨酯弹性纤维(氨纶)1.7%";
        List<String> textList = new ArrayList<>();
        List<String> percentageList = new ArrayList<>();

        separateTextAndPercentage(input, textList, percentageList);

        for (int i = 0; i < textList.size(); i++) {
            String text = textList.get(i);
            String percentage = percentageList.get(i);
            System.out.println("文字部分：" + text);
            System.out.println("百分比部分：" + percentage);
            System.out.println("---------------");
        }
    }

    public static void separateTextAndPercentage(String input, List<String> textList, List<String> percentageList) {
        String[] parts = input.split("、");
        Pattern pattern = Pattern.compile("(.*?)(\\d+(?:\\.\\d+)?%)");

        for (String part : parts) {
            Matcher matcher = pattern.matcher(part);
            if (matcher.find()) {
                String text = matcher.group(1).trim();
                String percentage = matcher.group(2).trim();
                textList.add(text);
                percentageList.add(percentage);
            }
        }
    }
}
