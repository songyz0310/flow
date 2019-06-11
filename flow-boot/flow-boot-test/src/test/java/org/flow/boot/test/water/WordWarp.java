package org.flow.boot.test.water;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import org.flow.boot.common.util.DateUtil;

/**
 * Created by uchoice on 2016/11/25.
 */
public class WordWarp {
    private static String separator = System.getProperty("line.separator");

    // 图片添加水印
    public static void writeWatermark(String srcPath, String outPath, Color contentColor, int fontSize, String content)
            throws IOException {
        // 读取原图片信息
        File srcImgFile = new File(srcPath);
        Image srcImg = ImageIO.read(srcImgFile);
        int srcImgWidth = srcImg.getWidth(null);
        int srcImgHeight = srcImg.getHeight(null);
        // 加水印
        BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2d = bufImg.createGraphics();
        graphics2d.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
        Font font = new Font("宋体", Font.PLAIN, fontSize);
        graphics2d.setColor(contentColor);// 根据图片的背景设置水印颜色

        graphics2d.setFont(font);

        int fontlength = graphics2d.getFontMetrics(graphics2d.getFont()).charsWidth(content.toCharArray(), 0,
                content.length());

        int line = fontlength / srcImgWidth;// 文字长度相对于图片宽度应该有多少行

        // 文字叠加,自动换行叠加
        int tempY = srcImgHeight - (line + 1) * fontSize - 10;
        int tempCharLen = 0;// 单字符长度
        int tempLineLen = 0;// 单行字符总长度临时计算
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < content.length(); i++) {
            char tempChar = content.charAt(i);

            tempCharLen = graphics2d.getFontMetrics(graphics2d.getFont()).charWidth(tempChar);

            tempLineLen += tempCharLen;
            if (sb.length() > 1 && (separator.contains(String.valueOf(tempChar)) || tempLineLen >= srcImgWidth)) {
                int tempX = srcImgWidth - tempLineLen;
                graphics2d.drawString(sb.toString(), tempX > 0 ? tempX : 0, tempY);

                sb.delete(0, sb.length());// 清空内容,重新追加

                tempY += fontSize;

                tempLineLen = 0;
            }

            sb.append(tempChar);// 追加字符
        }

        int tempX = srcImgWidth - tempLineLen;
        graphics2d.drawString(sb.toString(), tempX > 0 ? tempX : 0, tempY);

        graphics2d.dispose();

        // 输出图片
        FileOutputStream outImgStream = new FileOutputStream(outPath);
        ImageIO.write(bufImg, "jpg", outImgStream);
        outImgStream.flush();
        outImgStream.close();
    }

    public static void main(String[] args) throws IOException {
        // 原图位置, 输出图片位置, 水印文字颜色, 水印文字
        String now = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        String font = now + System.getProperty("line.separator")
                + "新疆维吾尔自治区乌鲁木齐市乌鲁木齐县苏州路西延段祥云中街566号新疆维吾尔自治区乌鲁木齐市乌鲁木齐县苏州路西延段祥云中街566号新疆维吾尔自治区乌鲁木齐市乌鲁木齐县苏州路西延段祥云中街566号";
        String file = "D:\\" + System.currentTimeMillis() + ".jpg";

        writeWatermark("D:\\test.jpg", file, Color.red, 20, font);

        String property = System.getProperty("line.separator");

        System.out.println(property);

    }
}
