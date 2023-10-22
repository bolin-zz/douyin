package com.douyin.test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HighQualityWhiteBackgroundImage {
    public static void main(String[] args) {
        // 原图的文件路径
        String imagePath = "E:\\e3huiman\\20230719637369073_750.jpg";

        // 读取原图
        BufferedImage originalImage;
        try {
            originalImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("读取原图失败：" + e.getMessage());
            return;
        }

        // 获取原图的宽度和高度
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // 创建一张新的 BufferedImage，使用 TYPE_INT_RGB 类型表示 RGB 颜色
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 获取 Graphics2D 上下文，用于绘制图形
        Graphics2D g2d = newImage.createGraphics();

        // 设置绘图背景颜色为白色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // 在白底图上绘制原图
        g2d.drawImage(originalImage, 0, 0, null);

        // 绘制完成后释放资源
        g2d.dispose();

        // 将生成的白底图保存为 PNG 格式的文件
        File outputFile = new File("E:\\e3huiman\\white_background_image.png");
        try {
            ImageIO.write(newImage, "png", outputFile);
            System.out.println("生成高清白底图成功，已保存为 white_background_image.png");
        } catch (IOException e) {
            System.out.println("保存白底图失败：" + e.getMessage());
        }
    }
}
