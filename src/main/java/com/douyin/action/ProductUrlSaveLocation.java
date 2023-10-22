package com.douyin.action;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.douyin.utils.StringUtils.createFileIfNotExists;
import static com.douyin.utils.StringUtils.saveLinkToLocal;

//网站的链接保存到记事本当中
public class ProductUrlSaveLocation {
    public static void main(String[] args) {

        //公共参数
//        String goodsKey = "Polo衫";
        String goodsKey = "T恤";
        String priceLow = "10";
        String priceHigh = "150";
        String savePath = "E:\\e3huiman\\" + goodsKey + priceLow + "-" + priceHigh + ".txt";

        // 调用创建文件的方法
        createFileIfNotExists(savePath);

        // 将字符串转换为整数
        int low = Integer.parseInt(priceLow);
        int high = Integer.parseInt(priceHigh);

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

        options.addArguments("--disable-gpu"); // 禁用 GPU 加速
        options.addArguments("--disable-extensions"); // 禁用扩展程序
        options.addArguments("--proxy-server='direct://'"); // 不使用代理
        options.addArguments("--proxy-bypass-list=*"); // 不绕过代理
        options.addArguments("--disable-infobars"); // 禁用信息栏
        options.addArguments("--disable-popup-blocking"); // 禁用弹出式窗口阻止
        options.addArguments("--disable-notifications"); // 禁用通知
        options.addArguments("--blink-settings=imagesEnabled=false"); // 禁用图片加载

        options.addArguments("--headless"); // 设置为无头模式

        // 创建WebDriver对象
        WebDriver driver = new ChromeDriver(service, options);

        // 最大化浏览器窗口
        driver.manage().window().maximize();
        // 打开网页
        driver.get("http://www.e3hui.com");
        driver.manage().window().maximize();

        // 等待网页加载完成
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 使用循环递增价格，直到达到或超过 priceHigh 的值
        for (int i = low; i <= high; i += 2) {

            // 在搜索框中输入关键字并提交搜索
            WebElement searchBox = driver.findElement(By.id("searchInput")); // 根据实际网页上的搜索框元素定位方式进行修改
            searchBox.sendKeys(goodsKey); // 将 "商品关键字" 替换为您要搜索的关键字
            searchBox.sendKeys(Keys.ENTER);

            WebElement priceLowBox = driver.findElement(By.id("priceLow"));
            WebElement priceHighBox = driver.findElement(By.id("priceHigh"));
            priceLowBox.sendKeys(Integer.toString(i));
            priceHighBox.sendKeys(Integer.toString(i + 2));
            priceHighBox.sendKeys(Keys.ENTER);


            // 定位包含页面总数信息的 <input> 元素
            WebElement pageCountInput = driver.findElement(By.id("pageCount"));

            // 获取页面总数的数值
            String pageCountText = pageCountInput.getAttribute("all");
            int pageCount = Integer.parseInt(pageCountText);
            System.out.println("当前价格：" + i + ", 总页数为： " + pageCount);

            for (int j = 1; j <= pageCount; j++) {
                String url = "http://www.e3hui.com/search/" + i + "," + (i + 1) + "/" + j + "?q=" + goodsKey + "&sort=defaultSort";
                driver.get(url);

                System.out.println(url);

                //提取网页的商品图链接  。<a href="/product/gesomkq.html?from=search" target="_blank"></a>
                String pageSource = driver.getPageSource();
                String regex = "href=\"\\/product.*?from=search";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(pageSource);
                List list = new ArrayList();
                while (matcher.find()) {
                    String link = matcher.group(0);
                    if (!list.contains(link)) {
                        list.add(link);
//                System.out.println("提取的链接: " + link);
                    }
                }

                // 等待搜索结果加载完成
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                List urlList = new ArrayList();
                //提取每个商品的链接
                for (int k = 0; k < list.size(); k++) {
                    String goodLink = (String) list.get(k);
                    goodLink = goodLink.replace("href=\"", "");

                    String linkUrl = "https://www.e3hui.com" + goodLink;

                    saveLinkToLocal(linkUrl, savePath);

                    // 关闭当前标签页
//            driver.close();
                }

            }


        }


        System.out.println("sdfsdfsdf");

        // 关闭浏览器
//        driver.quit();
    }
}
