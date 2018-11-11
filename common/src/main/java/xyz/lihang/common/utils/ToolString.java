package xyz.lihang.common.utils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理常用方法
 */
public abstract class ToolString {


    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z\\d!@#$%^&*]{5,17}$"; /*^(?![a-zA-z]+$)(?!\\d+$)(?![!@#$%^&*]+$)[a-zA-Z\\d!@#$%^&*]+$*/

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^1[0-9]{10}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";


    /**
     * 常用正则表达式：匹配非负整数（正整数 + 0）
     */
    public final static String regExp_integer_1 = "^\\d+$";

    /**
     * 常用正则表达式：匹配正整数
     */
    public final static String regExp_integer_2 = "^[0-9]*[1-9][0-9]*$";

    /**
     * 常用正则表达式：匹配非正整数（负整数  + 0）
     */
    public final static String regExp_integer_3 = "^((-\\d+) ?(0+))$";

    /**
     * 常用正则表达式：匹配负整数
     */
    public final static String regExp_integer_4 = "^-[0-9]*[1-9][0-9]*$";

    /**
     * 常用正则表达式：匹配整数
     */
    public final static String regExp_integer_5 = "^-?\\d+$";

    /**
     * 常用正则表达式：匹配非负浮点数（正浮点数 + 0）
     */
    public final static String regExp_float_1 = "^\\d+(\\.\\d+)?$";

    /**
     * 常用正则表达式：匹配正浮点数
     */
    public final static String regExp_float_2 = "^(([0-9]+\\.[0-9]*[1-9][0-9]*) ?([0-9]*[1-9][0-9]*\\.[0-9]+) ?([0-9]*[1-9][0-9]*))$";

    /**
     * 常用正则表达式：匹配非正浮点数（负浮点数 + 0）
     */
    public final static String regExp_float_3 = "^((-\\d+(\\.\\d+)?) ?(0+(\\.0+)?))$";

    /**
     * 常用正则表达式：匹配负浮点数
     */
    public final static String regExp_float_4 = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*) ?([0-9]*[1-9][0-9]*\\.[0-9]+) ?([0-9]*[1-9][0-9]*)))$";

    /**
     * 常用正则表达式：匹配浮点数
     */
    public final static String regExp_float_5 = "^(-?\\d+)(\\.\\d+)?$";

    /**
     * 常用正则表达式：匹配由26个英文字母组成的字符串
     */
    public final static String regExp_letter_1 = "^[A-Za-z]+$";

    /**
     * 常用正则表达式：匹配由26个英文字母的大写组成的字符串
     */
    public final static String regExp_letter_2 = "^[A-Z]+$";

    /**
     * 常用正则表达式：匹配由26个英文字母的小写组成的字符串
     */
    public final static String regExp_letter_3 = "^[a-z]+$";

    /**
     * 常用正则表达式：匹配由数字和26个英文字母组成的字符串
     */
    public final static String regExp_letter_4 = "^[A-Za-z0-9]+$";

    /**
     * 常用正则表达式：匹配由数字、26个英文字母或者下划线组成的字符串
     */
    public final static String regExp_letter_5 = "^\\w+$";

    /**
     * 常用正则表达式：匹配email地址
     */
    public final static String regExp_email = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";

    public final static String regExp_password = "^[a-zA-Z\\d!@#$%^&*.+/()_\\-=]{6,17}$";

    public final static String regExp_phone = "(1[0-9]{10})|(00[0-9]{8,15})";

    /**
     * 常用正则表达式：匹配url
     */
    public final static String regExp_url_1 = "^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$";

    /**
     * 常用正则表达式：匹配url
     */
    public final static String regExp_url_2 = "[a-zA-z]+://[^\\s]*";

    /**
     * 常用正则表达式：匹配中文字符
     */
    public final static String regExp_chinese_1 = "[\\u4e00-\\u9fa5]";

    /**
     * 常用正则表达式：匹配双字节字符(包括汉字在内)
     */
    public final static String regExp_chinese_2 = "[^\\x00-\\xff]";

    /**
     * 常用正则表达式：匹配空行
     */
    public final static String regExp_line = "\\n[\\s ? ]*\\r";

    /**
     * 常用正则表达式：匹配HTML标记
     */
    public final static String regExp_html_1 = "/ <(.*)>.* <\\/\\1> ? <(.*) \\/>/";

    /**
     * 常用正则表达式：匹配首尾空格
     */
    public final static String regExp_startEndEmpty = "(^\\s*) ?(\\s*$)";

    /**
     * 常用正则表达式：匹配帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)
     */
    public final static String regExp_accountNumber = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";

    /**
     * 常用正则表达式：匹配国内电话号码，匹配形式如 0511-4405222 或 021-87888822
     */
    public final static String regExp_telephone = "\\d{3}-\\d{8} ?\\d{4}-\\d{7}";

    /**
     * 常用正则表达式：腾讯QQ号, 腾讯QQ号从10000开始
     */
    public final static String regExp_qq = "[1-9][0-9]{4,}";

    /**
     * 常用正则表达式：匹配中国邮政编码
     */
    public final static String regExp_postbody = "[1-9]\\d{5}(?!\\d)";

    /**
     * 常用正则表达式：匹配身份证, 中国的身份证为15位或18位
     */
    public final static String regExp_idCard = "\\d{15} ?\\d{18}";

    /**
     * 常用正则表达式：IP
     */
    public final static String regExp_ip = "\\d+\\.\\d+\\.\\d+\\.\\d+";

    /**
     * 常用正则表达式：手机号
     */
    public final static String regExp_mobile = "^1[0-9]{10}$";
    /**
     * 匹配用户名
     */
    public final static String regExp_userName  = "(([\\u4E00-\\u9FA5]{2,7})|([a-zA-Z]{3,10}))";

    /**
     * 字符编码
     */
    public final static String encoding = "UTF-8";

    /**
     * 验证字符串是否匹配指定正则表达式
     *
     * @param content
     * @param regExp
     * @return
     */
    public static boolean regExpVali(String content, String regExp) {
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

    /**
     * double精度调整
     *
     * @param doubleValue 需要调整的值123.454
     * @param format      目标样式".##"
     * @return
     */
    public static String decimalFormat(double doubleValue, String format) {
        DecimalFormat myFormatter = new DecimalFormat(format);
        String formatValue = myFormatter.format(doubleValue);
        return formatValue;
    }



    /**
     * URL编码（utf-8）
     *
     * @param source
     * @return
     */
    public static String urlEncode(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据内容类型判断文件扩展名
     *
     * @param contentType 内容类型
     * @return
     */
    public static String getFileExt(String contentType) {
        String fileExt = "";
        if ("image/jpeg".equals(contentType)) {
            fileExt = ".jpg";

        } else if ("audio/mpeg".equals(contentType)) {
            fileExt = ".mp3";

        } else if ("audio/amr".equals(contentType)) {
            fileExt = ".amr";

        } else if ("video/mp4".equals(contentType)) {
            fileExt = ".mp4";

        } else if ("video/mpeg4".equals(contentType)) {
            fileExt = ".mp4";
        }

        return fileExt;
    }

    /**
     * 获取bean名称
     *
     * @param bean
     * @return
     */
    public static String beanName(Object bean) {
        String fullClassName = bean.getClass().getName();
        String classNameTemp = fullClassName.substring(fullClassName.lastIndexOf(".") + 1, fullClassName.length());
        return classNameTemp.substring(0, 1) + classNameTemp.substring(1);
    }

    public final static Pattern referer_pattern = Pattern.compile("@([^@^\\s^:]{1,})([\\s\\:\\,\\;]{0,1})");//@.+?[\\s:]


    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转大写
     *
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 使用逗号分隔的字符串转换为字符串数组
     *
     * @param s
     * @return
     */
    public static String[] toArray(String s, String sp) {
        String[] rs = null;
        if (s != null && s.indexOf(sp) != -1) {
            rs = s.split(sp);
        } else {
            rs = new String[]{s};
        }
        return rs;
    }


    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

}
