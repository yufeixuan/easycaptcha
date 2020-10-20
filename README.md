# EasyCaptcha

![JitPack](1.5.3)
![Hex.pm](https://img.shields.io/hexpm/l/plug.svg?style=flat-square)


## 1.简介

&emsp;&emsp;Java图形验证码，支持png透明验证码，支持gif验证码，支持base64验证码，中文验证码，可用于Java Web、JavaSE项目。


## 2.效果展示

**gif效果：**

![验证码](https://ws1.sinaimg.cn/large/006a7GCKly1ftpd30vgc2g303m01c0st.jpg) 
&emsp;&emsp;
![验证码](https://ws1.sinaimg.cn/large/006a7GCKly1ftpd22ixh9g303m01c3yj.jpg)
&emsp;&emsp;
![验证码](https://ws1.sinaimg.cn/large/006a7GCKly1ftpd2k0gjug303m01ct8t.jpg)

**png效果：**

![验证码](https://ws1.sinaimg.cn/large/006a7GCKly1ftpd09lqpaj303m01cwee.jpg)
&emsp;&emsp;
![验证码](https://ws1.sinaimg.cn/large/006a7GCKly1ftpd0tj6nhj303m01ct8m.jpg)
&emsp;&emsp;
![验证码](https://ws1.sinaimg.cn/large/006a7GCKly1ftpd1cw3pcj303m01c0sn.jpg)


**中文验证码：**

![验证码](https://ws1.sinaimg.cn/large/006a7GCKly1ftovnvbgx6g303m01cjrc.jpg)
&emsp;&emsp;
![验证码](https://ws1.sinaimg.cn/large/006a7GCKly1ftovojp2gng303m01cq2w.jpg)
&emsp;&emsp;
![验证码](https://ws1.sinaimg.cn/large/006a7GCKly1ftovp5u4c4g303m01cdft.jpg)

![验证码](https://ws1.sinaimg.cn/large/006a7GCKly1ftovmfxg71j303m01cq2s.jpg)
&emsp;&emsp;
![验证码](https://ws1.sinaimg.cn/large/006a7GCKly1ftovmy720dj303m01cq2s.jpg)
&emsp;&emsp;
![验证码](https://ws1.sinaimg.cn/large/006a7GCKly1ftovn68um6j303m01cglf.jpg)



## 3.导入项目
### 3.1.maven方式引入
在你的pom.xml中添加如下代码：
```xml
<dependency>
  <groupId>io.github.yufeixuan</groupId>
  <artifactId>easy-captcha</artifactId>
  <version>1.5.2</version>
</dependency>

```


---


## 4.使用方法

### 4.1.快速使用
1.在web.xml里面加入如下配置：
```xml
<web-app>
    <!-- 图形验证码servlet -->
    <servlet>
        <servlet-name>CaptchaServlet</servlet-name>
        <servlet-class>CaptchaServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>CaptchaServlet</servlet-name>
        <url-pattern>/images/captcha</url-pattern>
    </servlet-mapping>
</web-app>

```
2.前端代码
```html
<img src="/images/captcha" />
```

### 4.2.在SpringMVC中使用
也可以使用controller的形式输出验证码，方法如下：
```java
@Controller
public class MainController {
    
    @RequestMapping("/images/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }
}
```
前端代码：
```html
<img src="/images/captcha" />
```

### 4.3.判断验证码是否正确

```java
@Controller
public class LoginController {
    
    @PostMapping("/login")
    public JsonResult login(String username,String password,String code){
        
        if (!CaptchaUtil.ver(code, request)) {
            CaptchaUtil.clear(request);
            return JsonResult.error("验证码不正确");
        }
    }   
}
```

### 4.4.设置宽高和位数
```java
@Controller
public class MainController {
    
    @RequestMapping("/images/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置位数
        CaptchaUtil.out(5, request, response);
        
        // 设置宽、高、位数
        CaptchaUtil.out(130, 48, 5, request, response);
    }
}
```

### 4.5.不使用工具类

&emsp;&emsp;CaptchaUtil是为了简化操作，封装了生成验证码、存session、判断验证码等功能。CaptchaUtil使用的GifCaptcha
生成的字母数字混合的gif验证码，如果需要设置更多的参数，请参照如下操作使用：

```java
@Controller
public class MainController {
    
    @RequestMapping("/images/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置请求头为输出图片类型
        CaptchaUtil.setHeader(response);
        
        // 三个参数分别为宽、高、位数
        GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 5);
        
        // 设置字体
        gifCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置
        
        // 设置类型，纯数字、纯字母、字母数字混合
        gifCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        
        // 验证码存入session
        request.getSession().setAttribute("captcha", gifCaptcha.text().toLowerCase());
        
        // 输出图片流
        gifCaptcha.out(response.getOutputStream());
    }
    
    @PostMapping("/login")
    public JsonResult login(String username,String password,String code){
        // 获取session中的验证码
        String sessionCode = request.getSession().getAttribute("captcha");
        // 判断验证码
        if (code==null || !sessionCode.equals(code.trim().toLowerCase())) {
            return JsonResult.error("验证码不正确");
        }
    }  
}
```

## 5.更多设置

### 5.1.使用Gif验证码

```java
public class Test {
    
    public static void main(String[] args) {
        OutputStream outputStream = new FileOutputStream(new File("D:/a/aa.gif"));
        
        // 三个参数分别为宽、高、位数
        GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 5);
        
        // 设置字体
        gifCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置
        
        // 设置类型，纯数字、纯字母、字母数字混合
        gifCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        
        // 生成的验证码
        String code = gifCaptcha.text();
        
        // 输出图片流
        gifCaptcha.out(outputStream);
    }
}
```

### 5.2.使用png验证码

```java
public class Test {
    
    public static void main(String[] args) {
        OutputStream outputStream = new FileOutputStream(new File("D:/a/aa.png"));
        
        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        
        // 设置字体
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置
        
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        
        // 生成的验证码
        String code = specCaptcha.text();
        
        // 输出图片流
        specCaptcha.out(outputStream);
    }
}
```

### 5.3.验证码类型

 类型 | 描述 
 :--- | :--- 
 TYPE_DEFAULT | 数字和字母混合 
 TYPE_ONLY_NUMBER | 纯数字
 TYPE_ONLY_CHAR | 纯字母 
 TYPE_ONLY_UPPER | 纯大写字母
 TYPE_ONLY_LOWER | 纯小写字母
 TYPE_NUM_AND_UPPER | 数字和大写字母


### 5.4.中文验证码

中文png验证码：

```java
public class Test {
    
    public static void main(String[] args) {
        OutputStream outputStream = new FileOutputStream(new File("D:/a/aa.png"));
        
        // 三个参数分别为宽、高、位数
        ChineseCaptcha chineseCaptcha = new ChineseCaptcha(130, 48, 4);
        
        // 设置字体
        chineseCaptcha.setFont(new Font("楷体", Font.PLAIN, 28));  // 有默认字体，可以不用设置

        // 生成的验证码
        String code = chineseCaptcha.text();
        
        // 输出图片流
        chineseCaptcha.out(outputStream);
    }
}
```

中文gif验证码：

```java
public class Test {
    
    public static void main(String[] args) {
        OutputStream outputStream = new FileOutputStream(new File("D:/a/aa.png"));
        
        // 三个参数分别为宽、高、位数
        ChineseGifCaptcha chineseGifCaptcha = new ChineseGifCaptcha(130, 48, 4);
        
        // 设置字体
        chineseGifCaptcha.setFont(new Font("楷体", Font.PLAIN, 28));  // 有默认字体，可以不用设置

        // 生成的验证码
        String code = chineseGifCaptcha.text();
        
        // 输出图片流
        chineseGifCaptcha.out(outputStream);
    }
}
```
### 5.5.base64验证码
```java
public class Test {
    
    public static void main(String[] args) {
        String base64;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();


        SpecCaptcha specCaptcha = new SpecCaptcha();
        base64 = specCaptcha.base64(stream);

        System.out.println(base64);
       }
 }
```

### 5.6.前后端分离项目的使用

&emsp;&emsp;分离项目建议不要存储在session中，存储在redis中。


