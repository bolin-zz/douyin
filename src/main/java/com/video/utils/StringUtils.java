package com.video.utils;

import ws.schild.jave.*;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.info.VideoSize;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class StringUtils {

    /**
     * 它的作用是获取给定视频文件的宽度和高度。
     *
     * @param videoPath 接受一个参数 videoPath，该参数是要分析的视频文件的路径。
     */
    public static int[] getVideoDimensions(String videoPath) {
        int[] dimensions = new int[2]; // 存储宽度和高度

        File source = new File(videoPath);
        MultimediaObject multimediaObject = new MultimediaObject(source);

        try {
            MultimediaInfo info = multimediaObject.getInfo();
            VideoSize videoSize = info.getVideo().getSize();
            dimensions[0] = videoSize.getWidth(); // 宽度
            dimensions[1] = videoSize.getHeight(); // 高度
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dimensions;
    }

    /**
     * 它的作用是获取给定视频文件的时长（持续时间）
     *
     * @param videoPath 接受一个参数 videoPath，该参数是要分析的视频文件的路径。
     * @return 方法返回视频的持续时间（以毫秒为单位）。
     */
    public static long getVideoDuration(String videoPath) {
        File source = new File(videoPath);
        MultimediaObject multimediaObject = new MultimediaObject(source);

        try {
            MultimediaInfo info = multimediaObject.getInfo();
            long durationInMillis = info.getDuration();

            // 将毫秒转换为秒
            long durationInSeconds = durationInMillis / 1000;
            System.out.println("视频时长：" + durationInSeconds + " 秒");
            return durationInMillis;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 它提供了一种基于时间范围来剪切视频的方法，你需要计算出剪切的结束时间点，并将该时间点传递给 MultimediaObject 来剪切视频。
     *
     * @param inputPath
     * @param outputPath
     * @param targetWidth
     * @param targetHeight
     * @param cutDurationInSeconds
     */
    public static void cropVideo(String inputPath, String outputPath, int targetWidth, int targetHeight, long cutDurationInSeconds) {
        File source = new File(inputPath);
        File target = new File(outputPath);

        try {
            VideoAttributes videoAttributes = new VideoAttributes();
            videoAttributes.setCodec("libx264"); // 设置输出编解码器
            videoAttributes.setBitRate(1000000); // 设置比特率
            videoAttributes.setSize(new VideoSize(targetWidth, targetHeight)); // 设置目标宽度和高度

            AudioAttributes audioAttributes = new AudioAttributes();
            audioAttributes.setCodec("aac"); // 设置音频编解码器
            audioAttributes.setBitRate(192000); // 设置音频比特率

            EncodingAttributes encodingAttributes = new EncodingAttributes();
            encodingAttributes.setVideoAttributes(videoAttributes);
            encodingAttributes.setAudioAttributes(audioAttributes);

            // 计算剪切的开始时间和结束时间
            long cutStartTime = 0;
            long cutEndTime = cutDurationInSeconds - 1000;

            // 构建 FFmpeg 命令
            String ffmpegCommand = String.format("ffmpeg -i %s -ss %d -t %d -s %dx%d -c:v libx264 -b:v 1000k -c:a aac -b:a 192k %s",
                    inputPath, cutStartTime, cutEndTime, targetWidth, targetHeight, outputPath);

            Process process = Runtime.getRuntime().exec(ffmpegCommand);

            System.out.println("视频剪切完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String cropVideo(String inputPath, int targetWidth, int targetHeight, long cutDurationInSeconds) {
        File source = new File(inputPath);
        File target = new File("output.mp4"); // 输出视频文件路径

        try {
            VideoAttributes videoAttributes = new VideoAttributes();
            videoAttributes.setCodec("libx264"); // 设置输出编解码器
            videoAttributes.setBitRate(1000000); // 设置比特率
            videoAttributes.setSize(new VideoSize(targetWidth, targetHeight)); // 设置目标宽度和高度

            AudioAttributes audioAttributes = new AudioAttributes();
            audioAttributes.setCodec("aac"); // 设置音频编解码器
            audioAttributes.setBitRate(192000); // 设置音频比特率

            EncodingAttributes encodingAttributes = new EncodingAttributes();
            encodingAttributes.setVideoAttributes(videoAttributes);
            encodingAttributes.setAudioAttributes(audioAttributes);

            // 计算剪切的开始时间和结束时间
            long cutStartTime = 0;
            long cutEndTime = cutDurationInSeconds;

            // 构建 FFmpeg 命令
            String ffmpegCommand = String.format("ffmpeg -i %s -ss %d -t %d -s %dx%d -c:v libx264 -b:v 1000k -c:a aac -b:a 192k %s",
                    inputPath, cutStartTime, cutEndTime, targetWidth, targetHeight, target.getAbsolutePath());

            Process process = Runtime.getRuntime().exec(ffmpegCommand);
            long videoFileSize = getVideoFileSize(target.getAbsolutePath());
            while (videoFileSize < 500 * 1024) {// 500KB = 500 * 1024 字节
                videoFileSize = getVideoFileSize(target.getAbsolutePath());
                Thread.sleep(1000); // 1000毫秒 = 1秒
            }
            System.out.println("视频剪切完成");

            return target.getAbsolutePath(); // 返回剪切后的视频路径
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 要删除指定路径的文件，你可以使用 Java 的 File 类的 delete 方法。以下是一个示例代码，演示如何删除文件：
     *
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);

        // 检查文件是否存在
        if (file.exists()) {
            // 尝试删除文件
            if (file.delete()) {
                return true; // 文件删除成功
            } else {
                return false; // 文件删除失败
            }
        } else {
            // 文件不存在，不需要删除
            return true;
        }
    }

    /**
     * 要检测视频文件的大小，你可以使用 Java 的 File 类来获取文件的长度，视频文件的大小通常以字节为单位表示。以下是一个示例代码，演示如何检测视频文件的大小：
     *
     * @param videoFilePath
     * @return
     */
    public static long getVideoFileSize(String videoFilePath) {
        File videoFile = new File(videoFilePath);

        // 检查视频文件是否存在
        if (videoFile.exists() && videoFile.isFile()) {
            // 获取视频文件大小（以字节为单位）
            long size = videoFile.length();
            return size;
        } else {
            // 视频文件不存在或发生错误
            return -1;
        }
    }

    /**
     * 要检查视频文件是否存在，你可以使用 Java 的 File 类的 exists() 方法，就像检查普通文件一样。以下是一个示例代码，演示如何检查视频文件是否存在：
     *
     * @param videoFilePath
     * @return
     */
    public static boolean checkVideoFileExists(String videoFilePath) {
        File videoFile = new File(videoFilePath);

        // 使用 File 类的 exists() 方法检查视频文件是否存在
        return videoFile.exists();
    }

    /**
     * 在这个示例中，我们创建了一个名为 sleep 的静态方法，它接受一个整数参数 milliseconds，表示要休眠的毫秒数。方法内部调用 Thread.sleep 来进行休眠，并处理了可能的中断异常。
     *
     * @param milliseconds
     */
    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 我们创建了一个名为 readTxtFile 的方法，它接受文件路径作为参数，并返回文件的内容作为字符串。然后，在 main 方法中，我们调用 readTxtFile 方法并打印文件的内容。
     *
     * @param filePath
     * @return
     */
    public static String readTxtFile(String filePath) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

}
