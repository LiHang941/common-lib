package xyz.lihang.common.utils;

import java.util.LinkedList;

public class RandomUtils {

    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n 随机数个数
     */
    public static int[] randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            throw new RuntimeException("参数错误");
        }
        LinkedList<Integer> results = new LinkedList<>();
        for(int i=min;i<=max;i++){
            results.add(i);
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            //产生[0-results.size)
            int num = (int) (Math.random() * results.size());
            Integer res = results.remove(num);
            result[count] = res;
            ++count;
        }
        return result;
    }
}
