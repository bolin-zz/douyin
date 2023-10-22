package com.douyin;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;

import java.util.Arrays;
import java.util.List;

public class WebPageLoginAutomation {
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
            driver.get("https://fxg.jinritemai.com/login");
            String pageSource = driver.getPageSource();
            System.out.println(pageSource);

            // 获取当前网页的网址
            String currentURL = driver.getCurrentUrl();
            System.out.println("当前网页的网址：" + currentURL);

            // 等待网页包含特定关键字 "xxx"，最多等待 10 秒
            WebDriverWait wait = new WebDriverWait(driver, 10);
            boolean keywordFound = false;
            while (!keywordFound) {
                if (driver.getPageSource().contains("mshop")) {
                    keywordFound = true;
                } else {
                    try {
                        // 等待 5 秒后再重试
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            pageSource = driver.getPageSource();
            System.out.println("当前网页的源码：" + pageSource);

            // 使用 CSS Selector 定位元素
            WebElement element = driver.findElement(By.cssSelector("div[data-url='/ffa/g/create'][data-id='48']"));
            element.click();
            String dataUrlValue = element.getAttribute("data-url");
            System.out.println("data-url 属性值为：" + dataUrlValue);


            // 使用 JavaScriptExecutor 执行 JavaScript 脚本来获取动态生成的内容
            // 最大等待时间和轮询间隔
            int maxWaitTimeInSeconds = 20;
            int pollingIntervalInSeconds = 1;

            // 使用轮询等待获取目标元素
            WebElement dynamicElement = waitForElementWithPolling(driver, maxWaitTimeInSeconds, pollingIntervalInSeconds);

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
            dynamicElement = (WebElement) jsExecutor.executeScript("return document.getElementById('root');");
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
