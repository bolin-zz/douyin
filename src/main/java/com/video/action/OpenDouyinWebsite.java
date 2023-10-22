package com.video.action;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.video.utils.StringUtils.sleep;

public class OpenDouyinWebsite {

    public static void main(String[] args) {
        // 设置 ChromeDriver 路径
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");

        // 创建 ChromeDriverService
        ChromeDriverService service = new ChromeDriverService.Builder().build();

        // 创建 ChromeOptions 对象
        ChromeOptions options = new ChromeOptions();

        // 添加 Chrome DevTools Protocol (CDP) 参数
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        // 添加禁用兼容性提示的选项
        //options.addArguments("--disable-infobars");
        options.addArguments("disable-infobars");
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("disable-blink-features=AutomationControlled");
        // 添加禁止更新提示的选项
        options.addArguments("--disable-update");
        // 添加禁用显示通知的选项
        options.addArguments("--disable-notifications");
        // 设置用户代理为普通用户的代理
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.5790.102 Safari/537.36");

        // 创建WebDriver对象
        WebDriver driver = new ChromeDriver(service, options);


        // 打开指定的网址
        driver.get("https://www.douyin.com/");
        // 最大化浏览器窗口
        driver.manage().window().maximize();
        String pageSource = driver.getPageSource();
        sleep(5);
        // 使用 By.id 方法定位具有指定 id 属性值的元素
        WebElement divElement = driver.findElement(By.id("_7hLtYmO"));
        if (divElement != null && divElement.getText().contains("登录")) {
//            divElement = driver.findElement(By.id("_7hLtYmO"));
            sleep(10);
        }

        // 找到输入框元素
        WebElement inputElement = driver.findElement(By.xpath("//input[@data-e2e='searchbar-input']"));

        // 输入文本 "励志"
        inputElement.sendKeys("励志");

        // 模拟按下回车键，可以触发搜索或确认操作
        inputElement.sendKeys(Keys.ENTER);

        sleep(5);

        // 使用 XPath 查找具有特定 data-key 属性值的元素
        WebElement element = driver.findElement(By.xpath("//span[@data-key='video']"));
        // 点击该元素
        element.click();


        // 获取浏览器窗口的宽度和高度
        int windowWidth = driver.manage().window().getSize().getWidth();
        int windowHeight = driver.manage().window().getSize().getHeight();

        // 使用 JavaScript 将鼠标移到屏幕中间
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollTo(" + (windowWidth / 2) + "," + (windowHeight / 2) + ");");

        try {
            Thread.sleep(1000); // 等待 1 秒以确保鼠标移到中间
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 向下滚动 400px
        jsExecutor.executeScript("window.scrollBy(0, 400);");


        // 使用属性选择器定位具有特定 data-e2e 属性值的元素
        element = driver.findElement(By.cssSelector("[data-e2e='scroll-list']"));

        // 获取 <ul> 元素中的所有 <li> 元素
        List<WebElement> liElements = element.findElements(By.tagName("li"));

        // 遍历所有 <li> 元素并打印它们的文本内容
        for (WebElement li : liElements) {
            // 查找所有链接，其中 href 包含 "www.douyin.com" 的链接地址
            List<WebElement> links = li.findElements(By.cssSelector("a[href*='www.douyin.com']"));
            String douyinLinks = links.get(0).getAttribute("href");

            // 查找<div class="swoZuiEM">元素并获取其文本内容
            WebElement dvElement = driver.findElement(By.cssSelector("div.swoZuiEM"));
            String divText = dvElement.getText();

            // 查找<img>元素并获取其src属性值
            WebElement imgElement = driver.findElement(By.cssSelector("img"));
            String imgSrc = imgElement.getAttribute("src");


            System.out.println(li.getText());
        }


        System.out.println();

        // 关闭浏览器窗口（可选，根据你的需求决定是否关闭浏览器窗口）
        // driver.quit();
    }
}
