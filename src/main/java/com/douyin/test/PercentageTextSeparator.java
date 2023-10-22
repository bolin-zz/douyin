package com.douyin.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PercentageTextSeparator {
    public static void main(String[] args) {
        String input = "棉64%";
//        String input = "棉64%、聚酰胺纤维(锦纶)30%、聚氨酯弹性纤维(氨纶)6%";
//        String input = "聚酰胺纤维(锦纶)44.2%、粘胶纤维(粘纤)43.6%、聚氨酯弹性纤维(氨纶)7.5%、桑蚕丝4.7%";
        String[] result = getMaxPercentageAndText(input);

        if (result != null) {
            double maxPercentage = Double.parseDouble(result[0]);
            String maxPercentageText = result[1];

            System.out.println("最大百分比：" + maxPercentage + "%");
            System.out.println("对应的文字部分：" + maxPercentageText);
        } else {
            System.out.println("输入字符串为空或没有匹配到百分数。");
        }
    }

    public static String[] getMaxPercentageAndText(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        String[] parts = input.split("、");
        Pattern pattern = Pattern.compile("(.+?)\\((.*?)\\)(\\d+(?:\\.\\d+)?%)");

        double maxPercentage = -1;
        String maxPercentageText = "";

        for (String part : parts) {
            Matcher matcher = pattern.matcher(part);
            if (matcher.find()) {
                String text = matcher.group(1).trim(); // 取括号前的文字
                String bracketText = matcher.group(2).trim(); // 取括号内的文字
                String percentageStr = matcher.group(3).replace("%", "");
                double percentage = Double.parseDouble(percentageStr);

                if (percentage > maxPercentage) {
                    maxPercentage = percentage;
                    if (!bracketText.isEmpty()) {
                        maxPercentageText = bracketText;
                    } else {
                        maxPercentageText = text;
                    }
                }
            }
        }

        if (maxPercentageText.isEmpty()) {
            // 如果没有匹配到百分数，可以设置默认值或进行其他处理
            maxPercentageText = "没有匹配到百分数的默认值";
        }

        return new String[]{String.valueOf(maxPercentage), maxPercentageText};
    }
}
