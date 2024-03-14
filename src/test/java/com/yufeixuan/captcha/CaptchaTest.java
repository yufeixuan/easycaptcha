package com.yufeixuan.captcha;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

/**
 * 测试类
 * Created by phoenix on 2018-04-26 上午 10:08.
 */
public class CaptchaTest {

    @Test
    public void test() throws Exception {
        /*for (int i = 0; i < 5; i++) {
            SpecCaptcha specCaptcha = new SpecCaptcha();
            specCaptcha.setCharType(Captcha.TYPE_ONLY_UPPER);
            System.out.println(specCaptcha.text());
            specCaptcha.out(new FileOutputStream(new File("D:/Java/aa" + i + ".png")));
        }*/
    }

    @Test
    public void testGIf() throws Exception {
        for (int i = 0; i < 5; i++) {
            GifCaptcha gifCaptcha = new GifCaptcha();
            System.out.println(gifCaptcha.text());
            gifCaptcha.out(new FileOutputStream(new File("D:/Java/aa" + i + ".gif")));
        }
    }

    @Test
    public void testHan() throws Exception {
        /*for (int i = 0; i < 5; i++) {
            ChineseCaptcha chineseCaptcha = new ChineseCaptcha();
            System.out.println(chineseCaptcha.text());
            chineseCaptcha.out(new FileOutputStream(new File("D:/Java/aa" + i + ".png")));
        }*/

        ChineseCaptcha chineseCaptcha = new ChineseCaptcha();
        System.out.println(chineseCaptcha.text());
        chineseCaptcha.out(new FileOutputStream(new File("D:/Java/cc.png")));
    }

    @Test
    public void testGifHan() throws Exception {
        String base64;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        GifCaptcha captcha = new GifCaptcha();
//        String base64 = captcha.base64(stream);

//        ChineseCaptcha chineseCaptcha = new ChineseCaptcha();
//        base64 = chineseCaptcha.base64(stream);
//        ChineseGifCaptcha chineseGifCaptcha = new ChineseGifCaptcha();
//        base64 = chineseGifCaptcha.base64(stream);

        SpecCaptcha specCaptcha = new SpecCaptcha();
        base64 = specCaptcha.base64(stream);

        System.out.println(base64);
        /*for (int i = 0; i < 5; i++) {
            ChineseGifCaptcha chineseGifCaptcha = new ChineseGifCaptcha();
            System.out.println(chineseGifCaptcha.text());
            chineseGifCaptcha.out(new FileOutputStream(new File("D:/Java/aa" + i + ".gif")));
        }*/
    }

    @Test
    public void testPng() throws Exception {

        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);

        // 设置字体
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置

        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);


        // 生成的验证码
        String code = specCaptcha.text();

        // 输出图片流
        specCaptcha.out(new FileOutputStream(new File("/Users/pro/Documents/a/aa1.png")));
    }

    @Test
    public void testCpng() throws IOException {
        int width=256;
        int height=256;
        //创建BufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取Graphics2D
        Graphics2D g2d = image.createGraphics();

        // 增加下面代码使得背景透明
        image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = image.createGraphics();
        // 背景透明代码结束

        // 画图BasicStroke是JDK中提供的一个基本的画笔类,我们对他设置画笔的粗细，就可以在drawPanel上任意画出自己想要的图形了。
//        g2d.setColor(new Color(255, 0, 0));
//        g2d.setStroke(new BasicStroke(1f));
//        g2d.fillRect(128, 128, width, height);

        Random random = new Random();
        String strCode = "";
        for(int i=0;i<4;i++){
            String rand = "1";
            strCode = strCode + rand;
            g2d.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
            g2d.drawString(rand, 38*i+6, 46);
        }

        // 释放对象
        g2d.dispose();

        // 保存文件
        ImageIO.write(image, "png", new File("/Users/pro/Documents/a/test.png"));

    }

}
