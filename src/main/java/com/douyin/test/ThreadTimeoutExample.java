package com.douyin.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadTimeoutExample {
    public static void main(String[] args) {
        // 创建 ExecutorService，使用线程池大小为 5
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // 创建 Callable 任务列表
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            tasks.add(() -> {
                // 模拟任务执行，每个任务执行时间不同
                Thread.sleep(taskId * 1000);
                return "Task " + taskId + " completed!";
            });
        }

        try {
            // 提交任务并设置超时时间为 3 秒
            List<Future<String>> futures = executor.invokeAll(tasks, 3, TimeUnit.SECONDS);

            for (Future<String> future : futures) {
                try {
                    // 获取任务结果
                    String result = future.get();
                    System.out.println(result);
                } catch (CancellationException e) {
                    System.out.println("Task was cancelled due to timeout.");
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Task execution failed.");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 关闭 ExecutorService
            executor.shutdown();
        }
    }
}
