package com.douyin.test;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class RemoveDuplicatesFromFile {
    public static void main(String[] args) {
        String filePath = "E:\\e3huiman\\Polo衫10-150.txt";
        removeDuplicatesFromFile(filePath);
    }

    public static void removeDuplicatesFromFile(String filePath) {
        try {
            // 读取文件内容并保存到Set中
            Set<String> uniqueLines = new HashSet<>();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                uniqueLines.add(line);
            }
            reader.close();

            // 将Set中的数据写回到文件中
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (String uniqueLine : uniqueLines) {
                writer.write(uniqueLine);
                writer.newLine();
            }
            writer.close();

            System.out.println("去除重复数据成功！");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("处理文件出错！");
        }
    }
}
