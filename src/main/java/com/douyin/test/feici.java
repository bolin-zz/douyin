package com.douyin.test;

import com.douyin.utils.StringUtils;
import org.ansj.domain.Term;
import org.ansj.library.DicLibrary;
import org.ansj.splitWord.analysis.ToAnalysis;

public class feici {
    public static void main(String[] args) {
        String filePath = "E:\\projects\\douyinGather\\library\\custom_dictionary.txt";
        // 添加自定义词语
        StringUtils.importCustomDictionary(filePath);
        DicLibrary.reload(filePath);

        // 使用分词器进行分词
        String text = "捌零魔方2023年秋冬新款抗起球绢丝绒薄款圆领纯色针织衫肌理感宽松简约时尚毛衣 货号：112922";
//        System.out.println(ToAnalysis.parse(text));

        // 使用分词器进行分词
        for (Term term : ToAnalysis.parse(text)) {
            // 获取词语部分
            String word = term.getName();
            System.out.print(word + " ");
        }
    }
}
