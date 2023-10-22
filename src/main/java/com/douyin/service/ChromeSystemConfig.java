package com.douyin.service;

import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;

/**
 * chrome网页配置
 */
public class ChromeSystemConfig {

    public static void config() {
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
    }
}
