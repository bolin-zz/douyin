package com.video.action;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.video.utils.StringUtils.readTxtFile;

public class OpenWebPage {
    public static void main(String[] args) {

        String filePath = "E:\\video\\video.txt"; // 替换为你的 txt 文件路径
        String fileContent = readTxtFile(filePath);

        // 使用正则表达式匹配<li>元素
        String liPattern = "<li class=\"TgJeUdJ2 B9KMVC9A\">(.*?)</li>";
        Pattern pattern = Pattern.compile(liPattern, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(fileContent);

        while (matcher.find()) {
            String liElementHtml = matcher.group(1);

            // 使用正则表达式提取链接
            String linkPattern = "<a href=\"(.*?)\"";
            Pattern linkPatternRegex = Pattern.compile(linkPattern);
            Matcher linkMatcher = linkPatternRegex.matcher(liElementHtml);

            if (linkMatcher.find()) {
                String link = linkMatcher.group(1);
                System.out.println("链接: " + link);
            }

            // 使用正则表达式提取文字
            String textPattern = "<div class=\"swoZuiEM\">(.*?)</div>";
            Pattern textPatternRegex = Pattern.compile(textPattern, Pattern.DOTALL);
            Matcher textMatcher = textPatternRegex.matcher(liElementHtml);

            if (textMatcher.find()) {
                String text = textMatcher.group(1);
                System.out.println("文字: " + text);
            }

            String srcPattern = "<img\\s+[^>]*?src\\s*=\\s*['\"]([^'\"]*?)['\"][^>]*?>";
            Pattern imgPattern = Pattern.compile(srcPattern);
            Matcher imgMatcher = imgPattern.matcher(liElementHtml);
            if (imgMatcher.find()) {
                String imgUrl = imgMatcher.group(1);
                System.out.println("图片: " + imgUrl);
            }
        }




//        // 使用属性选择器定位具有特定 data-e2e 属性值的元素
//        WebElement element = driver.findElement(By.cssSelector("[data-e2e='scroll-list']"));
//
//        // 获取 <ul> 元素中的所有 <li> 元素
//        List<WebElement> liElements = element.findElements(By.tagName("li"));
//
//        // 遍历所有 <li> 元素并打印它们的文本内容
//        for (WebElement li : liElements) {
//            // 查找所有链接，其中 href 包含 "www.douyin.com" 的链接地址
//            List<WebElement> links = li.findElements(By.cssSelector("a[href*='www.douyin.com']"));
//            String douyinLink = links.get(0).getAttribute("href");
//            System.out.println(douyinLink);
//            // 查找<div class="swoZuiEM">元素并获取其文本内容
//            WebElement divElement = driver.findElement(By.cssSelector("div.swoZuiEM"));
//            String divText = divElement.getText();
//            System.out.println(divText);
//
//            // 查找<img>元素并获取其src属性值
//            WebElement imgElement = driver.findElement(By.cssSelector("img"));
//            String imgSrc = imgElement.getAttribute("src");
//            System.out.println(imgSrc);
//
//            System.out.println(li.getText());
//        }


        System.out.println("123");
    }
}
