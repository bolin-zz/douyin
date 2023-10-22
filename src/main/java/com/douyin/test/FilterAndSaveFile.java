package com.douyin.test;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class FilterAndSaveFile {
    public static void main(String[] args) {
        String sourceFilePath1 = "E:\\projects2023\\e3huiman\\T恤以上传.txt";
        String sourceFilePath2 = "E:\\projects2023\\e3huiman\\T恤10-150--备份.txt";
        String targetFilePath = "E:\\projects2023\\e3huiman\\aaaa.txt";

        Set<String> filterSet = readFileLines(sourceFilePath1);
        filterAndSaveFilteredLines(sourceFilePath2, targetFilePath, filterSet);
    }

    public static Set<String> readFileLines(String filePath) {
        Set<String> lines = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void filterAndSaveFilteredLines(String sourceFilePath, String targetFilePath, Set<String> filterSet) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(targetFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!filterSet.contains(line)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
