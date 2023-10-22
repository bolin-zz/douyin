package com.video.action;



import ws.schild.jave.*;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.File;
import java.io.IOException;

public class VideoAddVideoConverter {

    public static void main(String[] args) {
        String inputFilePath = "E:\\video\\d.mp4"; // 输入视频文件路径
        String disturbanceFilePath = "E:\\video\\disturbance.mp4"; // 干扰层视频文件路径
        String outputFilePath = "E:\\video\\ok\\d_with_disturbance.mp4"; // 输出视频文件路径

        // 编码原始视频
        encodeAndAddDisturbance(inputFilePath, disturbanceFilePath, outputFilePath);

        System.out.println("视频合并完成！");
    }

    public static void encodeAndAddDisturbance(String sourcePath, String disturbancePath, String outputFilePath) {
        File sourceFile = new File(sourcePath);
        File disturbanceFile = new File(disturbancePath);

        try {
            File tempOutputFile = File.createTempFile("output", ".mp4");
            VideoAddVideo(sourceFile, disturbanceFile, tempOutputFile);

            // 编码合并后的视频
            encodeVideo(tempOutputFile.getAbsolutePath(), outputFilePath);

            // 删除临时文件
            tempOutputFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void VideoAddVideo(File baseVideo, File overlayVideo, File outputFile) {
        String ffmpegCommand = String.format("ffmpeg -i %s -i %s -filter_complex overlay %s", baseVideo, overlayVideo, outputFile);

        try {
            Process process = Runtime.getRuntime().exec(ffmpegCommand);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void encodeVideo(String sourcePath, String outputFilePath) {
        File source = new File(sourcePath);
        File target = new File(outputFilePath);

        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("aac");
        audio.setBitRate(236000 / 2);
        audio.setChannels(2);
        audio.setSamplingRate(8000);

        VideoAttributes video = new VideoAttributes();
        video.setCodec("h264");
        video.setBitRate(1000000);
        video.setFrameRate(25);
        video.setQuality(4);

        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat("mp4");
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);

        Encoder encoder = new Encoder();
        try {
            encoder.encode(new MultimediaObject(source), target, attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
