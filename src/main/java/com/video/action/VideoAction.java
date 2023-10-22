package com.video.action;

import java.io.IOException;

import static com.video.utils.StringUtils.*;

public class VideoAction {
    public static void main(String[] args) {

        String backgroundVideoPath = "E:\\video\\disturbance.mp4"; // 替换为顶层背景视频路径
        String overlayVideoPath = "E:\\video\\d.mp4"; // 替换为底层叠加视频路径
        String outputVideoPath = "E:\\video\\output.mp4"; // 输出合成视频路径

        composeVideos(overlayVideoPath, backgroundVideoPath, outputVideoPath);
    }

    public static void composeVideos(String overlayVideo, String backgroundVideo, String outputVideo) {
        int[] videoDimensions = getVideoDimensions(overlayVideo);

        int videoDuration = (int) getVideoDuration(overlayVideo) / 1000 + 1;

        String tempPathVideo = cropVideo(backgroundVideo, videoDimensions[0], videoDimensions[1], videoDuration);

//        String ffmpegCommand = String.format("ffmpeg -i %s -i %s -filter_complex \"[0:v]scale=iw:ih[v0];[1:v][v0]overlay=0:0:enable='between(t,0," + videoDuration + ")'\" %s",
//                overlayVideo, tempPathVideo, outputVideo);
        String ffmpegCommand = String.format("ffmpeg -i %s -i %s -filter_complex \"[0:v]scale=iw:ih[v0];[1:v][v0]overlay=0:0:enable='between(t,0," + videoDuration + ")'\" %s",
                overlayVideo, tempPathVideo, outputVideo);


        try {
            Process process = Runtime.getRuntime().exec(ffmpegCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (checkVideoFileExists(outputVideo)) {
            System.out.println("视频文件存在");
            deleteFile(tempPathVideo);
        } else {
            System.out.println("视频文件不存在");
        }

    }
}
