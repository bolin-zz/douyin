package com.video.action;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.Arrays;

import static com.video.utils.StringUtils.sleep;

public class OpenWithF12Website {
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

        //设置语言头：
        options.addArguments("--lang=en-US");
        //添加自定义标头
        options.addArguments("--header=Accept-Language: en-US");


        // 创建WebDriver对象
        WebDriver driver = new ChromeDriver(service, options);


        // 打开指定的网址
        driver.get("https://www.douyin.com/search/%E5%8A%B1%E5%BF%97?publish_time=0&sort_type=0&source=switch_tab&type=video");
        // 最大化浏览器窗口
        driver.manage().window().maximize();

        sleep(10);
        // 定位具有 data-key 属性值为 "video" 的 <span> 元素
        WebElement videoElement = driver.findElement(By.cssSelector("span[data-key='video']"));

        // 模拟按下F12键
//        videoElement.sendKeys(Keys.F12);

//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("window.dispatchEvent(new KeyboardEvent('keydown', { key: 'F12' }))");

        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).sendKeys("i").keyUp(Keys.CONTROL).keyUp(Keys.SHIFT).perform();


//        Actions actions = new Actions(driver);
//        actions.sendKeys(Keys.F12).perform();


    }
}
