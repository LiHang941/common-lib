package com.github.lihang941.common.redis;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/8/30
 */
public class CreateKey {

    private CreateKey() {
    }

    private static String KEY_HEAD = null;


    public static void setHead(String head){
        KEY_HEAD = head;
    }

    private static String getKeyHead(){
        if (KEY_HEAD == null)
            throw new RuntimeException("没有设置KEY_HEAD");
        return KEY_HEAD;
    }

    public static String create(String... keys) {
        StringBuilder b = new StringBuilder(getKeyHead());
        for (String key : keys) {
            b.append(key);
            b.append("::");
        }
        return b.toString();
    }

    public static String createKey(String keyHead, String... keys) {
        StringBuilder b = new StringBuilder(getKeyHead());
        for (String key : keys) {
            b.append(key);
            b.append("::");
        }
        return b.toString();
    }

}
