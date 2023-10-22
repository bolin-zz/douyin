package com.douyin.utils;

import org.ansj.library.DicLibrary;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    /**
     * 接收参数 p，11是其它费用加快递费，乘3是提高到三倍，再做折扣，并根据公式计算 lastprice，然后返回计算结果。
     *
     * @param p是传入价格
     * @return lastprice
     */
    public static int calculateLastPrice(int p) {
        return (p + 11) * 3 * 100;
    }

    public static void saveLinkToLocal(String linkUrl, String savePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(savePath, true)); // 设置第二个参数为true，表示以追加模式打开文件
            writer.write(linkUrl + System.lineSeparator()); // 加上换行符，每个链接占一行
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保存链接失败：" + e.getMessage());
        }
    }


    public static void createFileIfNotExists(String filePath) {
        File file = new File(filePath);

        try {
            if (!file.exists()) {
                // 文件不存在，创建文件
                if (file.createNewFile()) {
                    System.out.println("文件创建成功！");
                } else {
                    System.out.println("文件创建失败！");
                }
            } else {
                System.out.println("文件已存在，无需创建。");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中读取内容，并且将读取的内容按行存储在一个 List 中，供后续使用。
     *
     * @param filePath
     * @return list
     */
    public static List<String> readFromFile(String filePath) {
        List<String> fileContent = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.add(line);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContent;
    }

    /**
     * 我们将waitForPageLoad方法的参数列表中添加了一个整数参数waitTime，用于表示等待时间。
     * 在main方法中，我们通过调用waitForPageLoad(2000)方法，并传递等待时间为2000毫秒（2秒）。
     * 这样就可以实现根据需要传递不同的等待时间值来等待网页加载完成。你可以根据实际需求调整传递的等待时间值。
     *
     * @param waitTime
     */
    public static void waitForPageLoad(int waitTime) {
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 识别图片是否具有白色底图的方法示例：
     * 识别图片是否有白色底图可以通过图像处理的方式来实现。一种常见的方法是计算图像的颜色分布或像素值来判断是否为白色底图。
     * 以下是一个简单的示例，使用Java的ImageIO和BufferedImage类来读取图片并进行简单的颜色分析：
     * 请注意，上述代码只是一个简单的示例，它假设白色像素的RGB值接近 (255, 255, 255)。在实际应用中，你可能需要根据实际情况调整判断条件，以便更准确地识别白色底图。
     * 此外，还可以考虑使用图像处理库如OpenCV等来实现更复杂的图像识别和分析。
     *
     * @param imagePath
     * @return
     */
    public static boolean hasWhiteBackground(String imagePath) {
        try {
            // 读取图片文件
            //BufferedImage image = ImageIO.read(new File(imagePath));

            // 从URL读取图片
            URL url = new URL(imagePath);
            BufferedImage image = ImageIO.read(url);

            // 获取图片的宽度和高度
            int width = image.getWidth();
            int height = image.getHeight();

            // 统计白色像素的数量
            int whitePixelCount = 0;

            // 遍历图片的每个像素
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = image.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;

                    // 判断像素是否接近白色（假设白色的RGB值是 (255, 255, 255)）
                    if (red > 240 && green > 240 && blue > 240) {
                        whitePixelCount++;
                    }
                }
            }

            // 根据白色像素数量来判断是否为白色底图
            return whitePixelCount > (width * height * 0.9);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 将链表中的元素拼接成字符串，限制不超过 maxCount 个元素
    public static String joinElements(LinkedList<String> list, int maxCount) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (String element : list) {
            if (count < maxCount) {
                sb.append(element);
                if (count < maxCount - 1) {
                    sb.append("|");
                }
                count++;
            } else {
                break;
            }
        }
        return sb.toString();
    }

    public static int getRandomStockNum(Random random) {
        return random.nextInt(1000) + 1000;
    }


    public static String[] parseColors(String input) {
        List<String> colorList = new ArrayList<>();

        // 先找到"颜色|"和"^尺码|"之间的字符串，即颜色部分
        int colorStart = input.indexOf("颜色|");
        String colorString = input.replace("颜色|", "");

        // 使用逗号分割字符串并去除空格
        String[] colorArray = colorString.split(",");
        for (String color : colorArray) {
            colorList.add(color.trim());
        }

        return colorList.toArray(new String[0]);
    }

    // 方法1：去除货币符号
    public static double removeCurrencySymbol(String priceStr) {
        // 使用正则表达式去除货币符号
        priceStr = priceStr.replaceAll("[^0-9.]", "");
        return Double.parseDouble(priceStr);
    }

    // 方法2：计算价格
    public static double calculatePrice(double price) {
        return (price + 11) * 3;
    }

    /**
     * 自定义词语的导入。请注意，该方法假设自定义词语文本文件的格式为：词语\t词性\t词频，每个词语占一行。
     * 创建一个文本文件（例如：custom_dictionary.txt）并将自定义词语写入其中，每个词语占一行：
     * 自定义词语1
     * 自定义词语2
     * 自定义词语3
     * ...
     * 请注意，自定义词语文本文件应该是一个纯文本文件，使用 UTF-8 或其他支持的字符编码保存。
     * 每行的词语、词性和词频之间使用制表符分隔，不要使用空格或其他字符进行分隔。
     *
     * @param filePath
     */
    public static void importCustomDictionary(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 3) {
                    String word = parts[0];
                    String nature = parts[1];
                    int frequency = Integer.parseInt(parts[2]);
                    DicLibrary.insert(DicLibrary.DEFAULT, word, nature, frequency);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 中文字提取
     *
     * @param String型内容
     * @return String
     */
    public static String extractChineseWords(String input) {
        String regex = "[\\u4e00-\\u9fa5]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        StringBuilder chineseWords = new StringBuilder();
        while (matcher.find()) {
            chineseWords.append(matcher.group()).append(" ");
        }
        return chineseWords.toString().trim();
    }

    /**
     * 中文字提取
     *
     * @param String型内容
     * @return List
     */
    public static List<String> extractChineseWordsList(String input) {
        String regex = "[\\u4e00-\\u9fa5]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        List<String> chineseWordsList = new ArrayList<>();
        while (matcher.find()) {
            chineseWordsList.add(matcher.group());
        }
        return chineseWordsList;
    }

    /**
     * 一个中文字符通常由两个字节表示，所以我们可以通过判断字符串的长度（字节数）来判断中文字符的个数。如果字符串的字节长度大于或等于4，那么它至少包含两个中文字符。
     * <p>
     * 以下是一个示例方法，用于判断字符串中中文字符的个数是否大于或等于2：
     *
     * @param String
     * @return boolean
     */
    public static boolean isChineseStringLengthGreaterThanOrEqualToTwo(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        int chineseCharCount = 0;
        for (char c : input.toCharArray()) {
            if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
                chineseCharCount++;
            }
        }

        return chineseCharCount >= 2;
    }

    /**
     * 它将接收一个字符串作为参数，并返回字符串的长度：
     * 在这个示例中，我们创建了一个名为getStringLength的方法，它接收一个字符串str作为参数，
     * 并在方法体中使用length()方法来获取字符串的长度。如果传入的字符串为null，则方法返回0。
     *
     * @param str
     * @return int
     */
    public static int getStringLength(String str) {
        if (str == null || str == "" || str.isEmpty()) {
            return 0;
        }
        return str.length();
    }

    // 辅助方法：查找元素是否存在，存在则返回该元素，否则返回null
    private static WebElement findElementIfExists(WebDriver driver, By by) {
        try {
            return driver.findElement(by);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * String = "pic1.jpg|pic2.jpg|pic3.jpg"转化成List
     *
     * @param fileNames
     * @return List
     */
    public static List<String> convertToList(String fileNames) {
        List<String> fileList = new ArrayList<>();
        if (fileNames != null && !fileNames.isEmpty()) {
            String[] fileNameArray = fileNames.split("\\|");
            for (String fileName : fileNameArray) {
                fileList.add(fileName);
            }
        }
        return fileList;
    }

    /**
     * 根据名称 "模板名称" 取得 template_id 的方法如下：
     * 代码会遍历 component_template_info_list 中的每个模板，找到名称为 "模板名称" 的模板，并输出其对应的 template_id。
     *
     * @param jsonData是GetProductComponentTemplate返回的。
     * @param targetTemplateName是：模板名称
     * @return template_id
     */
    public static long getTemplateIdByName(String jsonData, String targetTemplateName) {
        long templateId = -1;

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray templateInfoList = jsonObject.getJSONObject("data").getJSONArray("component_template_info_list");

            for (int i = 0; i < templateInfoList.length(); i++) {
                JSONObject templateInfo = templateInfoList.getJSONObject(i);
                String templateName = templateInfo.getString("template_name");
                if (templateName.equals(targetTemplateName)) {
                    templateId = templateInfo.getLong("template_id");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return templateId;
    }

    /**
     * 根据字符是否为中文字符来决定计算长度。如果是中文字符，计数增加2，否则增加1。这样，中文字符将被计算为两个字符长度，其他字符将被计算为一个字符长度。
     * <p>
     * 请注意，这只是一种简单的方法来处理中文字符和其他字符的长度计算。在实际应用中，您可能需要根据需求进一步调整和优化。
     *
     * @param str传入的字符
     * @return str字符的长度
     */
    public static int getCharacterCount(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }

        int charCount = 0;
        for (char c : str.toCharArray()) {
            if (isChineseCharacter(c)) {
                charCount += 2;
            } else {
                charCount++;
            }
        }

        return charCount;
    }

    public static boolean isChineseCharacter(char c) {
        // 根据Unicode编码范围判断是否是中文字符
        return c >= 0x4E00 && c <= 0x9FFF;
    }

    /**
     * 你可以将调用 getPageAttrNameReplace(pageAttrName) 传入一个包含"所有人群"的字符串，然后它会返回一个替换后的字符串，其中"所有人群"被替换为"通用"。
     *
     * @param pageAttrName传入值
     * @return String替换后的值
     */
    public static String getPageAttrNameReplace(String pageAttrName) {
        pageAttrName = pageAttrName.replace("所有人群", "通用");
        return pageAttrName;
    }


    /**
     * 包含这个词则返回true
     *
     * @param attrTitle
     * @return boolean
     */
    public static boolean extractedTitle(String attrTitle) {
        if (attrTitle.contains("日常") || attrTitle.contains("常规") || attrTitle.contains("其他")) {
            return true;
        }
        return false;
    }

    /**
     * 使用正则表达式来匹配并提取这个价格区间中的所有数字，然后从中选择最大值。以下是使用 Java 代码实现的方法：
     *
     * @param html
     * @return
     */
    public static ArrayList<Double> extractPrices(String html) {
        ArrayList<Double> prices = new ArrayList<>();

        Pattern pattern = Pattern.compile("￥(\\d+(\\.\\d+)?)");
        Matcher matcher = pattern.matcher(html);

        while (matcher.find()) {
            String priceStr = matcher.group(1);
            double price = Double.parseDouble(priceStr);
            prices.add(price);
        }

        return prices;
    }

    public static double findMaxPrice(ArrayList<Double> prices) {
        double maxPrice = Double.MIN_VALUE;

        for (double price : prices) {
            if (price > maxPrice) {
                maxPrice = price;
            }
        }

        return maxPrice;
    }

    /**
     * 保存链接到本地文件，然后在需要的地方调用该方法。下面是一个示例方法的实现：
     *
     * @param link     链接
     * @param filePath 保存链接的路径
     */
    public static void saveLinkToFile(String link, String filePath) {
        if (link == null || link.isEmpty()) {
            return; // 不保存空链接
        }

        try {
            FileWriter fileWriter = new FileWriter(filePath, true); // 打开文件以追加内容
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(link); // 写入链接到文件
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 你可以使用 Java 的 Thread.sleep() 方法结合随机数来实现随机等待的功能。以下是一个示例代码，演示如何随机等待 minSeconds 到 maxSeconds 秒之间的时间：
     *
     * @param minSeconds 最小时间
     * @param maxSeconds 最多时间
     */
    public static void randomWait(int minSeconds, int maxSeconds) {
        try {
            if (minSeconds < 0 || maxSeconds < minSeconds) {
                throw new IllegalArgumentException("Invalid input for wait time range");
            }

            int minWaitTime = minSeconds * 1000; // 转换为毫秒
            int maxWaitTime = maxSeconds * 1000; // 转换为毫秒
            Random random = new Random();
            int waitTime = random.nextInt(maxWaitTime - minWaitTime + 1) + minWaitTime;

            Thread.sleep(waitTime);

//            System.out.println("随机等待了 " + (waitTime / 1000) + " 秒");
        } catch (InterruptedException e) {
            System.out.println("randomWait异常：" + e.getMessage());
        }
    }

    /**
     * 代码中，我们将打开网页、输入关键字、点击图片等操作封装在了 openAndClickImage() 方法中。
     * 然后在 main() 方法中调用了这个方法。这样，你可以在其他地方也调用 openAndClickImage() 方法来执行相同的操作。
     *
     * @param driver
     */
    public static void openAndClickImage(WebDriver driver) {
        try {
            // 打开网页
            driver.get("http://www.e3hui.com/");

            // 定位搜索输入框
            WebElement searchInput = driver.findElement(By.id("searchInput"));

            // 输入关键字并触发搜索
            searchInput.sendKeys("T恤" + Keys.RETURN);

            // 等待一段时间，你可以加入自己的等待逻辑

            // 定位图片元素
            WebElement imageElement = driver.findElement(By.cssSelector(".img-box a"));
            // 点击图片元素
            imageElement.click();

            randomWait(2, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 您可以使用上述的 getMaxPercentageAndText 方法来实现输出最大百分数和对应的文字部分。以下是如何修改代码来实现这一目标：
     * 这个方法会匹配括号内的文字作为输出，如果括号内没有内容，则会使用括号前的文字作为输出。
     *
     * @param input "聚酰胺纤维(锦纶)44.2%、粘胶纤维(粘纤)43.6%、聚氨酯弹性纤维(氨纶)7.5%、桑蚕丝4.7%"
     * @return
     */

    public static String[] getMaxPercentageAndText(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        String[] parts = input.split("、");
        Pattern pattern = Pattern.compile("(?:\\((.*?)\\))?\\s*(\\d+(?:\\.\\d+)?)%");

        double maxPercentage = -1;
        String maxPercentageText = "";

        for (String part : parts) {
            Matcher matcher = pattern.matcher(part);
            if (matcher.find()) {
                String bracketText = matcher.group(1);
                String text = bracketText != null ? bracketText : part;
                double percentage = Double.parseDouble(matcher.group(2));

                if (percentage > maxPercentage) {
                    maxPercentage = percentage;
                    maxPercentageText = text;
                }
            }
        }

        if (maxPercentageText.isEmpty()) {
            // 如果没有匹配到百分数，可以设置默认值或进行其他处理
            maxPercentageText = "没有匹配到百分数的默认值";
        }

        return new String[]{extractTextFromPercentage(maxPercentageText), String.valueOf(maxPercentage)};
    }

    /**
     * 以下是一个用于分离百分数中的文字并输出的方法：
     * 您可以调用 extractTextFromPercentage 方法并传入百分数字符串作为参数，它会返回提取的文字部分
     * @param input
     * @return
     */
    public static String extractTextFromPercentage(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        Pattern pattern = Pattern.compile("([\\p{L}\\s]+)\\s*(\\d+(?:\\.\\d+)?)%");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1).trim();
        }

        return input;
    }
    /**
     * 在这个示例中，identifyPercentageRange 方法根据输入的百分数判断它属于哪个分断，
     * 然后返回相应的描述。你可以根据需要调用这个方法并传入不同的百分数进行测试。
     *
     * @param percentage百分数
     * @return 序列号
     */
    public static int identifyPercentageRange(double percentage) {
        if (percentage <= 29) {
            return 0;
        } else if (percentage >= 95) {
            return 1;
        } else if (percentage >= 29 && percentage <= 39) {
            return 2;
        } else if (percentage >= 39 && percentage <= 49) {
            return 3;
        } else if (percentage >= 49 && percentage <= 59) {
            return 4;
        } else if (percentage >= 59 && percentage <= 69) {
            return 5;
        } else if (percentage >= 69 && percentage <= 79) {
            return 6;
        } else if (percentage >= 79 && percentage <= 89) {
            return 7;
        } else {
            return 8;
        }
    }

    /**
     * 在这个示例中，identifyPercentageRange 方法根据输入的百分数判断它属于哪个分断，
     * 然后返回相应的描述。你可以根据需要调用这个方法并传入不同的百分数进行测试。
     *
     * @param percentage百分数
     * @return 字符
     */
    public static String identifyPercentageRangeRetrueString(double percentage) {
        if (percentage <= 29) {
            return "29%及以下";
        } else if (percentage >= 95) {
            return "95%及以上";
        } else if (percentage >= 29 && percentage <= 39) {
            return "29%-39%(含)";
        } else if (percentage >= 39 && percentage <= 49) {
            return "39%-49%(含)";
        } else if (percentage >= 49 && percentage <= 59) {
            return "49%-59%(含)";
        } else if (percentage >= 59 && percentage <= 69) {
            return "59%-69%(含)";
        } else if (percentage >= 69 && percentage <= 79) {
            return "69%-79%(含)";
        } else if (percentage >= 79 && percentage <= 89) {
            return "79%-89%(含)";
        } else {
            return "89%-95%(含)";
        }
    }

    /**
     * 创建了一个名为 getFabricText 的方法，它接收一个 WebDriver 对象作为参数，然后执行定位元素和获取文本的操作。
     * 你可以将该方法放入你的测试代码中，然后调用它来获取面料材质文本。
     * @param driver
     * @return fabricText
     */
    public static String getFabricText(WebDriver driver) {
        // 使用 findElements 定位元素列表
        List<WebElement> fabricTextElements = driver.findElements(By.cssSelector(".content-box-fabric .cont"));
        String fabricText = "";
        if (!fabricTextElements.isEmpty()) {
            // 元素存在，获取文本值
            fabricText = fabricTextElements.get(0).getText();
        }
        return fabricText;
    }
}

