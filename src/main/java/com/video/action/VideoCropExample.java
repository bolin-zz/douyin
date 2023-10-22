package com.video.action;
import ws.schild.jave.*;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.info.VideoSize;

import java.io.File;

import static com.video.utils.StringUtils.getVideoDimensions;

public class VideoCropExample {

    public static void main(String[] args) {
        String inputVideoPath = "E:\\video\\disturbance.mp4"; // 输入视频文件路径
        String outputVideoPath = "E:\\video\\output.mp4"; // 输出视频文件路径
//        String backgroundVideoPath = "E:\\video\\disturbance.mp4"; // 替换为顶层背景视频路径
//        String overlayVideoPath = "E:\\video\\d.mp4"; // 替换为底层叠加视频路径
        int targetWidth = 140; // 目标宽度
        int targetHeight = 360; // 目标高度
        int cutDurationInSeconds = 10; // 剪切的秒数

        cropVideo(inputVideoPath, outputVideoPath, targetWidth, targetHeight, cutDurationInSeconds);
    }

    public static void cropVideo(String inputPath, String outputPath, int targetWidth, int targetHeight, int cutDurationInSeconds) {
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
            int cutStartTime = 0;
            int cutEndTime = cutDurationInSeconds;

            // 构建 FFmpeg 命令
            String ffmpegCommand = String.format("ffmpeg -i %s -ss %d -t %d -s %dx%d -c:v libx264 -b:v 1000k -c:a aac -b:a 192k %s",
                    inputPath, cutStartTime, cutEndTime, targetWidth, targetHeight, outputPath);

            Process process = Runtime.getRuntime().exec(ffmpegCommand);

            System.out.println("视频剪切完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}