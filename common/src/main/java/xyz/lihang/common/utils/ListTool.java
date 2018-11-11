package xyz.lihang.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 集合操作
 * @author : lihang1329@gmail.com
 * @since : 2018/9/10
 */
public class ListTool {

    // 交集
    public static <T> List<T> intersect(List<T> ls, List<T> ls2) {
        List<T> list = new ArrayList<>(ls);
        list.retainAll(ls2);
        return list;
    }

    // 并集
    public static <T> List<T> union(List<T> ls, List<T> ls2) {
        List<T> list = new ArrayList<>(ls);
        list.addAll(ls2);
        return list;
    }

    // 差集
    public static <T> List<T> diff(List<T> ls, List<T> ls2) {
        List<T> list = new ArrayList<>(ls);
        list.removeAll(ls2);
        return list;
    }
}
