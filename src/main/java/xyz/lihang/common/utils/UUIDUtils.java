package xyz.lihang.common.utils;

import java.util.UUID;

/**
 * UUID工具类
 */
public class UUIDUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getUUIDByLength(int length){
        if(length <= 0 || length > 32){
            return null;
        }
        return getUUID().substring(0,length);
    }

}
