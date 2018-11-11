package xyz.lihang.common.utils;

import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/8
 */
public class RandomCode {


    /**
     * 自定义进制（选择你想要的进制数，不能重复且最好不要0、1这些容易混淆的字符）
     */
    private char[] r;

    /**
     * 定义一个字符用来补全邀请码长度（该字符前面是计算出来的邀请码，后面是用来补全用的）
     */
    private char completion;

    /**
     * 邀请码长度
     */
    private int len;

    private String completionFinal;

    public RandomCode(int len, char completion, String decimal, String completionFinal) {
        this.len = len;
        this.completion = completion;
        this.r = decimal.toCharArray();
        this.completionFinal = completionFinal;
        if (completionFinal.length() < len) {
            //throw new RuntimeException("长度应该大于len");
        }
    }

    public RandomCode() {
        this(6, 'a', "qwe8s2dzx9c7p5k3mjufr4vytn6bgh", "qwertyuiop");
    }


    /**
     * 根据ID生成随机码
     *
     * @param id ID
     * @return 随机码
     */
    public String toSerialCode(long id) {
        char[] buf = new char[32];
        int charPos = 32;

        while ((id / r.length) > 0) {
            int ind = (int) (id % r.length);
            buf[--charPos] = r[ind];
            id /= r.length;
        }
        buf[--charPos] = r[(int) (id % r.length)];
        String str = new String(buf, charPos, (32 - charPos));
        // 不够长度的自动随机补全
        if (str.length() < len) {
            StringBuilder sb = new StringBuilder();
            sb.append(completion);
            Random rnd = getRandom();
            for (int i = 1; i < len - str.length(); i++) {
                sb.append(r[rnd.nextInt(r.length)]);
            }
            str += sb.toString();
        }
        return str;
    }


    /**
     * 根据ID生成固定六位随机码
     *
     * @param id ID
     * @return 随机码
     */
    public String toSerialCodeFinal(long id) {
        UUID uuid = UUID.randomUUID();
        char[] buf = new char[32];
        int charPos = 32;

        while ((id / r.length) > 0) {
            int ind = (int) (id % r.length);
            buf[--charPos] = r[ind];
            id /= r.length;
        }
        buf[--charPos] = r[(int) (id % r.length)];
        String str = new String(buf, charPos, (32 - charPos));
        // 不够长度的自动补全
        if (str.length() < len) {
            StringBuilder sb = new StringBuilder();
            sb.append(completionFinal.subSequence(0, len - str.length()));
            str += sb.toString();
        }
        return str;
    }


    /**
     * 根据随机码生成ID
     *
     * @param code
     * @return ID
     */
    public long codeToId(String code) {
        char chs[] = code.toCharArray();
        long res = 0L;
        for (int i = 0; i < chs.length; i++) {
            int ind = 0;
            for (int j = 0; j < r.length; j++) {
                if (chs[i] == r[j]) {
                    ind = j;
                    break;
                }
            }
            if (chs[i] == completion) {
                break;
            }
            if (i > 0) {
                res = res * r.length + ind;
            } else {
                res = ind;
            }
        }
        return res;
    }


    public static String getUUIDString() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getUUIDByLength(int length){
        if(length <= 0 || length > 32){
            return null;
        }
        return getUUIDString().substring(0,length);
    }


    private static class RANDOM_INSTANCE {
        static Random random = new Random();
    }

    public static Random getRandom(){
        return RANDOM_INSTANCE.random;
    }


    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     *
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n   随机数个数
     */
    public static int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            throw new RuntimeException("参数错误");
        }
        LinkedList<Integer> results = new LinkedList<>();
        for (int i = min; i <= max; i++) {
            results.add(i);
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            //产生[0-results.size)
            int num = (int) (Math.random() * results.size());
            Integer res = results.remove(num);
            result[count] = res;
            ++count;
        }
        return result;
    }



}
