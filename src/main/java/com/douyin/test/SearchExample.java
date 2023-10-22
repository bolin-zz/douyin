package com.douyin.test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SearchExample {

    public static void main(String[] args) {
        // 设置ChromeDriver路径
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");

        // 创建WebDriver对象
        WebDriver driver = new ChromeDriver();

        try {
            // 打开网页
            driver.get("http://www.e3hui.com/");

            // 定位搜索输入框
            WebElement searchInput = driver.findElement(By.id("searchInput"));

            // 输入关键字并触发搜索
            searchInput.sendKeys("T恤" + Keys.RETURN);
            // 定位图片元素
            WebElement imageElement = driver.findElement(By.cssSelector(".img-box a"));
            // 点击图片元素
            imageElement.click();

        } finally {
            // 关闭浏览器
            driver.quit();
        }
    }
}
