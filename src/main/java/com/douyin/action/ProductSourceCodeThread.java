package com.douyin.action;

import com.douyin.service.ChromeSystemConfig;
import com.douyin.service.ProcessProductTask;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.douyin.utils.StringUtils.readFromFile;

/**
 * 商品创建，多线程
 */
public class ProductSourceCodeThread {
    public static void main(String[] args) {

        String categoryName = "T恤";
        String templateName = "男士T恤模板";
        int cateId = 0; // 初始化cateid,会在代码中根据categoryname去查找出cateid..替换成你要查询的商品类目ID，20172为T恤
        String accessToken = "9910333d-f72d-4174-88c9-f687a157da3e"; // 替换成你的Access Token
        String apiKey = "bolin20230727";

        String filePath = "E:\\projects2023\\e3huiman\\T恤10-150.txt";
        String saveExceptionLinkFilePath = "E:\\projects2023\\e3huiman\\saveExceptionLinkFilePath.txt";

        // 读取商品链接列表
        List<String> urls = readFromFile(filePath);

        // 创建固定大小的线程池
        int maxThreads = 10; // 最大线程数
        ExecutorService executor = Executors.newFixedThreadPool(maxThreads);

        AtomicInteger completedTasks = new AtomicInteger(0); // 使用AtomicInteger来跟踪已完成的任务数量
        // 遍历商品链接列表，为每个链接创建一个线程任务并提交到线程池
        for (String url : urls) {
            Runnable task = new ProcessProductTask(url, categoryName, templateName, cateId, accessToken, apiKey, saveExceptionLinkFilePath);
            executor.submit(() -> {
                try {
                    task.run();
                } finally {
                    int count = completedTasks.incrementAndGet(); // 递增已完成的任务数量
                    if (count % 100 == 0) {
                        System.out.println("----------------------------------------------已完成任务数量：" + count+" -------------------------------------------");
                    }
                }
            });
        }

        try {
            // 等待所有任务完成并设置超时时间为 90 秒
            executor.awaitTermination(90, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("main异常：" + e.getMessage());
        } finally {
            // 关闭 ExecutorService
            executor.shutdown();
        }

        System.out.println("所有任务完成");
    }


}
