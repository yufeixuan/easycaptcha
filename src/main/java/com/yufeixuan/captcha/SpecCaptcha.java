package com.yufeixuan.captcha;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

/**
 * png格式验证码
 * Created by phoenix on 2019-04-26 上午 10:08.
 */
public class SpecCaptcha extends Captcha {

    public SpecCaptcha() {
    }

    public SpecCaptcha(int width, int height) {
        this();
        setWidth(width);
        setHeight(height);
    }

    public SpecCaptcha(int width, int height, int len) {
        this(width, height);
        setLen(len);
    }

    public SpecCaptcha(int width, int height, int len, Font font) {
        this(width, height, len);
        setFont(font);
    }

    /**
     * 生成验证码
     *
     * @param out 输出流
     * @return 是否成功
     */
    @Override
    public boolean out(OutputStream out) {
        checkAlpha();
        return graphicsImage(textChar(), out);
    }

    @Override
    public String base64(OutputStream os) {
        checkAlpha();
        return graphicsImage2(textChar(), os);
    }

    /**
     * 生成验证码图形
     *
     * @param strs 验证码
     * @param out  输出流
     * @return boolean
     */
    private boolean graphicsImage(char[] strs, OutputStream out) {
        boolean ok;
        try {
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bi.createGraphics();

            // 透明背景
            bi = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            g.dispose();

            // 创建新的画布
            g = bi.createGraphics();

//            g.setColor(Color.WHITE);
//            g.fillRect(0, 0, width, height);
            // 抗锯齿
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setStroke(new BasicStroke(1.3f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            // 随机画干扰线
            drawLine(3, g);
            // 随机画干扰圆
            drawOval(8, g);
            // 画字符串
            AlphaComposite ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);// 指定透明度
            g.setComposite(ac3);

            int hp = (height - font.getSize()) >> 1;
            int h = height - hp;
            int w = width / strs.length;
            int sp = (w - font.getSize()) / 2;
            for (int i = 0; i < strs.length; i++) {
                g.setColor(new Color(20 + num(110), 20 + num(110), 20 + num(110)));
                // 计算坐标
                int x = i * w + sp + num(3);
                int y = h - num(3, 6);
                if (x < 8) {
                    x = 8;
                }
                if (x + font.getSize() > width) {
                    x = width - font.getSize();
                }
                if (y > height) {
                    y = height;
                }
                if (y - font.getSize() < 0) {
                    y = font.getSize();
                }
                g.setFont(font.deriveFont(num(2) == 0 ? Font.PLAIN : Font.ITALIC));
                g.drawString(String.valueOf(strs[i]), x, y);
            }

            g.dispose();
            ImageIO.write(bi, "png", out);
            out.flush();
            ok = true;
        } catch (IOException e) {
            ok = false;
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ok;
    }

    private String graphicsImage2(char[] strs, OutputStream out) {
        ByteArrayOutputStream stream = (ByteArrayOutputStream) out;
        String ok = null;
        try {
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bi.createGraphics();

            // 透明背景
            bi = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            g.dispose();

            // 创建新的画布
            g = bi.createGraphics();

//            g.setColor(Color.WHITE);
//            g.fillRect(0, 0, width, height);
            // 抗锯齿
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setStroke(new BasicStroke(1.3f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            // 随机画干扰线
            drawLine(3, g);
            // 随机画干扰圆
            drawOval(8, g);
            // 画字符串
            AlphaComposite ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);// 指定透明度
            g.setComposite(ac3);

            int hp = (height - font.getSize()) >> 1;
            int h = height - hp;
            int w = width / strs.length;
            int sp = (w - font.getSize()) / 2;
            for (int i = 0; i < strs.length; i++) {
                g.setColor(new Color(20 + num(110), 20 + num(110), 20 + num(110)));
                // 计算坐标
                int x = i * w + sp + num(3);
                int y = h - num(3, 6);
                if (x < 8) {
                    x = 8;
                }
                if (x + font.getSize() > width) {
                    x = width - font.getSize();
                }
                if (y > height) {
                    y = height;
                }
                if (y - font.getSize() < 0) {
                    y = font.getSize();
                }
                g.setFont(font.deriveFont(num(2) == 0 ? Font.PLAIN : Font.ITALIC));
                g.drawString(String.valueOf(strs[i]), x, y);
            }

            g.dispose();
            ImageIO.write(bi, "png", out);
            stream.flush();
            byte[] encode = Base64.getEncoder().encode(stream.toByteArray());
            ok = new String(encode);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ok;
    }
}