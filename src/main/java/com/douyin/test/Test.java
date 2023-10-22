package com.douyin.test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        // 设置ChromeDriver的路径
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

        // 最大化浏览器窗口
        driver.manage().window().maximize();

        try {
            // 打开网页
            driver.get("https://www.cnblogs.com/tiaowangdeying/p/10491755.html");
            String pageSource = driver.getPageSource();
            System.out.println(pageSource);

            // 使用 JavaScriptExecutor 执行 JavaScript 脚本来获取动态生成的内容
            // 最大等待时间和轮询间隔
            int maxWaitTimeInSeconds = 20;
            int pollingIntervalInSeconds = 1;

            // 使用轮询等待获取目标元素
            WebElement dynamicElement = waitForElementWithPolling(driver, maxWaitTimeInSeconds, pollingIntervalInSeconds);
            System.out.println(dynamicElement.getText());
            // 如果获取到了目标元素，使用 Actions 类将鼠标移动到目标元素上
            if (dynamicElement != null) {
                Actions actions = new Actions(driver);
                actions.moveToElement(dynamicElement).perform();

                // 等待一段时间，观察鼠标移动到指定元素上的效果
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("未找到目标元素。");
            }

        } finally {
            // 关闭WebDriver
            driver.quit();
        }
    }

    private static WebElement waitForElementWithPolling(WebDriver driver, int maxWaitTimeInSeconds, int pollingIntervalInSeconds) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement dynamicElement = null;
        int timePassed = 0;

        while (timePassed < maxWaitTimeInSeconds) {
            dynamicElement = (WebElement) jsExecutor.executeScript("return document.getElementById('topics');");
//            dynamicElement = (WebElement) jsExecutor.executeScript("return document.getElementsByClassName('styles_requireImg__2X0BY')[0];");
            if (dynamicElement != null) {
                return dynamicElement;
            }

            try {
                Thread.sleep(pollingIntervalInSeconds * 1000);
                timePassed += pollingIntervalInSeconds;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
