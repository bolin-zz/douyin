package com.video.action;

import java.io.IOException;

import static com.video.utils.StringUtils.getVideoDimensions;
import static com.video.utils.StringUtils.getVideoDuration;

public class VideoCompositionExample {

    public static void main(String[] args) {
        String backgroundVideoPath = "E:\\video\\disturbance.mp4"; // 替换为顶层背景视频路径
        String overlayVideoPath = "E:\\video\\d.mp4"; // 替换为底层叠加视频路径
        String outputVideoPath = "E:\\video\\output.mp4"; // 输出合成视频路径

        composeVideos(overlayVideoPath, backgroundVideoPath, outputVideoPath);
    }

    public static void composeVideos(String overlayVideo, String backgroundVideo, String outputVideo) {
        getVideoDimensions(backgroundVideo);
        getVideoDimensions(overlayVideo);

        long videoDuration = getVideoDuration(overlayVideo);

        String ffmpegCommand = String.format("ffmpeg -i %s -i %s -filter_complex \"[0:v]scale=iw:ih[v0];[1:v][v0]overlay=0:0:enable='between(t,0," + videoDuration + ")'\" %s",
                overlayVideo, backgroundVideo, outputVideo);
//        "[1:v]scale=iw:ih[v1];[0:v][v1]overlay=0:0:enable='between(t,5,10)'" output.mp4
//        ffmpeg -i background.mp4 -i overlay.mp4 -filter_complex "[1:v]scale=iw:ih[v1];[1:v]crop=iw:iw*(main_h/main_w)[v2];[0:v][v2]overlay=0:0:enable='between(t,5,10)'" output.mp4
//        ffmpeg -i background.mp4 -i overlay.mp4 -filter_complex "[1:v]crop=iw:ih[v1];[0:v][v1]overlay=0:0:enable='between(t,5,10)'" output.mp4



        try {
            Process process = Runtime.getRuntime().exec(ffmpegCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
