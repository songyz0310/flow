package org.flow.boot.test.water;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@SuppressWarnings("restriction")
public class WaterSet {
    /**
     * 给图片+图片水印
     *
     * @param url --url 地址
     * @param pressImg -- 水印图片
     * @param targetImg -- 目标文件
     * @param location 水印位置：left-top：左上角，right-top：右上角，left-bottom：左下角，right-bottom：右下角
     * @param degree 水印旋转角度
     */

    public static void pressImage(String url, String pressImg, String targetImg, String location, Integer degree) {
        try {
            // 目标文件
            File _file = toFile(url, new File(targetImg));
            Image src = ImageIO.read(_file);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            // 水印文件
            File _filebiao = new File(pressImg);
            Image src_biao = ImageIO.read(_filebiao);
            int width_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            // 水印坐标
            int x = 0, y = 0;
            if (StringUtils.equals(location, "left-top")) {
                x += 30;
                y += 30;
            }
            else if (StringUtils.equals(location, "right-top")) {
                x = width - width_biao - 30;
                y += 30;
            }
            else if (StringUtils.equals(location, "left-bottom")) {
                y = height - height_biao - 30;
                x += 30;
            }
            else if (StringUtils.equals(location, "right-bottom")) {
                x = width - width_biao - 30;
                y = height - height_biao - 30;
            }
            else {
                x = (width - width_biao) / 2;
                y = (height - height_biao) / 2;
            }
            if (null != degree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree), x, y);
            }
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.drawImage(src_biao, x, y, width_biao, height_biao, null);
            // 水印文件结束
            g.dispose();
            // 直接修改源文件
            FileOutputStream out = new FileOutputStream(targetImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.flush();
            out.close();
            // 生成新的文件
            // File sf = new File("D:/imgout/" + "test" + "." + "jpg");
            // ImageIO.write(image, "jpg", sf); // 保存图片
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给图片加文字水印
     *
     * @param url 图片的网络地址
     * @param pressText s水印文字
     * @param targetImg 目标文件
     * @param fontName 字体名称
     * @param fontStyle 字体风格
     * @param fontSize 字体大小
     * @param location 字体位置:left-top：左上角，right-top：右上角，left-bottom：左下角，right-bottom：右下角
     */

    public static void pressText(String url, String pressText, String targetImg, String fontName, int fontStyle,
            int fontSize, String location, Color color) {
        try {

            int textWidth = getFontWidth(fontName, fontStyle, fontSize, pressText);
            // File _file = new File(targetImg);
            File _file = toFile(url, new File(targetImg));
            Image src = ImageIO.read(_file);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.drawImage(src.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.45f));
            int x = 0, y = 0;
            if (StringUtils.equals(location, "left-top")) {
                x = 30;
                y = 30;
            }
            else if (StringUtils.equals(location, "right-top")) {
                x = width - textWidth - 30;
                y = 30;
            }
            else if (StringUtils.equals(location, "left-bottom")) {
                x += 30;
                y = height - 30;
            }
            else if (StringUtils.equals(location, "right-bottom")) {
                x = width - textWidth - 30;
                y = height - 30;
            }
            else {
                x = (width - textWidth) / 2;
                y = (height) / 2;
            }
            g.drawString(pressText, x, y);
            g.dispose();
            FileOutputStream out = new FileOutputStream(targetImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.flush();
            out.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 文件保存
     *
     * @param link 图片链接
     * @param file 存入的文件地址
     * @return 下载的文件
     */
    public static File toFile(String link, File file) {
        try {

            if (file.exists() == false) {
                file.createNewFile();
            }

            URL url = new URL(link);
            URLConnection uri = url.openConnection();
            // 获取数据流
            InputStream ins = uri.getInputStream();
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
            return file;
        }
        catch (Exception e) {
            return null;
        }

    }

    /**
     * 计算文本占用的width
     *
     * @param fontName 字体名称
     * @param fontStyle 字体风格
     * @param fontSize 字体大小
     * @param pressText 输入文本
     * @return 文字所占用的宽带
     */
    public static int getFontWidth(String fontName, int fontStyle, int fontSize, String pressText) {
        Font f = new Font(fontName, fontStyle, fontSize);
        FontMetrics fm = sun.font.FontDesignMetrics.getMetrics(f);
        // 高度
        // System.out.println(fm.getHeight());
        // 单个字符宽度
        // System.out.println(fm.charWidth('A'));
        // 整个字符串的宽度
        // System.out.println(fm.stringWidth(pressText));
        return fm.stringWidth(pressText);

    }

    public static void main(String[] args) {

        String file = "D:\\" + System.currentTimeMillis() + ".jpg";

        pressText("file:D:\\test.jpg", "宋印赠宋印赠宋印赠宋印赠宋印赠宋印赠宋印赠宋印赠宋印赠宋印赠宋印赠宋印赠宋印赠宋印赠宋印赠宋印赠", file, "黑体",
                Font.BOLD + Font.ITALIC, 30, "right-bottom", Color.GRAY);
        // getFontWidth("黑体", Font.BOLD + Font.ITALIC, 30, "miraclesgrocery");
        // System.out.println(Color.RED);
        // System.out.println(Color.WHITE);
        // System.out.println(Color.GRAY);
    }

}
