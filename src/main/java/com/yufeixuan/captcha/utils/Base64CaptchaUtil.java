package com.yufeixuan.captcha.utils;

import com.yufeixuan.captcha.Captcha;
import com.yufeixuan.captcha.GifCaptcha;
import com.yufeixuan.captcha.SpecCaptcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 图形验证码工具类
 * Created by phoenix on 2019-04-26 上午 10:08.
 */
public class Base64CaptchaUtil {
    private static final String SESSION_KEY = "captcha_phoenix";

    private static final Pattern CHARACTER_PATTERN  = Pattern.compile("\\s*|\t|\r|\n");
    /**
     * 验证验证码
     *
     * @param code    用户输入的验证码
     * @param request HttpServletRequest
     * @return 是否正确
     */
    public static boolean ver(String code, HttpServletRequest request) {
        if (code != null && !code.trim().isEmpty()) {
            String captcha = (String) request.getSession().getAttribute(SESSION_KEY);
            return code.trim().toLowerCase().equals(captcha);
        }
        return false;
    }

    /**
     * 输出验证码
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    public static String out(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        return out(5, request, response);
    }

    /**
     * 输出验证码
     *
     * @param len      长度
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    public static String out(int len, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        return out(130, 48, len, request, response);
    }

    /**
     * 输出验证码
     *
     * @param len      长度
     * @param font     字体
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    public static String out(int len, Font font, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        return out(130, 48, len, font, request, response);
    }

    /**
     * 输出验证码
     *
     * @param width    宽度
     * @param height   高度
     * @param len      长度
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    public static String out(int width, int height, int len, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        return out(width, height, len, null, request, response);
    }

    /**
     * 输出验证码
     *
     * @param width    宽度
     * @param height   高度
     * @param len      长度
     * @param font     字体
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    public static String out(int width, int height, int len, Font font, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        return outCaptcha(width, height, len, font, 1, request, response);
    }

    /**
     * 输出验证码
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    public static String outPng(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        return outPng(5, request, response);
    }

    /**
     * 输出验证码
     *
     * @param len      长度
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    public static String outPng(int len, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        return outPng(130, 48, len, request, response);
    }

    /**
     * 输出验证码
     *
     * @param len      长度
     * @param font     字体
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    public static String outPng(int len, Font font, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        return outPng(130, 48, len, font, request, response);
    }

    /**
     * 输出验证码
     *
     * @param width    宽度
     * @param height   高度
     * @param len      长度
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    public static String outPng(int width, int height, int len, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        return outPng(width, height, len, null, request, response);
    }

    /**
     * 输出验证码
     *
     * @param width    宽度
     * @param height   高度
     * @param len      长度
     * @param font     字体
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    public static String outPng(int width, int height, int len, Font font, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        return outCaptcha(width, height, len, font, 0, request, response);
    }

    /**
     * 输出验证码
     *
     * @param width    宽度
     * @param height   高度
     * @param len      长度
     * @param font     字体
     * @param cType    类型
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    private static String outCaptcha(int width, int height, int len, Font font, int cType, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        setHeader(response, cType);
        Captcha captcha = null;
        if (cType == 0) {
            captcha = new SpecCaptcha(width, height, len);
        } else if (cType == 1) {
            captcha = new GifCaptcha(width, height, len);
        }
        if (font != null) {
            captcha.setFont(font);
        }
        request.getSession().setAttribute(SESSION_KEY, captcha.text().toLowerCase());
        if (cType == 0) {
            return "data:image/png;base64," + replaceBlank(captcha.base64(stream));
        } else {
            return "data:image/gif;base64," + replaceBlank(captcha.base64(stream));
        }
    }

    /**
     * 清除session中的验证码
     *
     * @param request HttpServletRequest
     */
    public static void clear(HttpServletRequest request) {
        request.getSession().removeAttribute(SESSION_KEY);
    }

    /**
     * 设置相应头
     *
     * @param response HttpServletResponse
     */
    public static void setHeader(HttpServletResponse response, int type) {
        if (type == 0){
            response.setContentType("image/png");
        }
        else{
            response.setContentType("image/gif");
        }
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
    }

    /**
     * 去除特殊字符
     * @param str base64字符串
     * @return
     */
    private static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Matcher m = CHARACTER_PATTERN.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

}
