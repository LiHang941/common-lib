package com.github.lihang941.common.redis;

/**
 * CreateKey 升级版
 *
 * @author : lihang1329@gmail.com
 * @since : 2019/3/25
 */
public class GenerateKey {

    private GenerateKey() {
    }
    private static final String DEFAULT_KEY_HEAD = "default:generate:key";
    private static final String DEFAULT_SPLICE = ":";
    private static String KEY_HEAD = null;
    private static String KEY_SPLICE = null;

    public static void setHead(String head) {
        KEY_HEAD = head;
    }

    public static void setSplice(String splice) {
        KEY_SPLICE = splice;
    }


    private static String getKeyHead() {
        if (KEY_HEAD == null)
            return DEFAULT_KEY_HEAD;
        return KEY_HEAD;
    }

    private static String getSplice() {
        if (KEY_SPLICE == null)
            return DEFAULT_SPLICE;
        return KEY_SPLICE;
    }


    public static String generate(String... keys) {
        return _createKey(getKeyHead(), getSplice(), keys);
    }

    public static String generate(String keyHead, String... keys) {
        return _createKey(keyHead, getSplice(), keys);
    }

    public static String generate(String keyHead, String splice, String... keys) {
        return _createKey(keyHead, splice, keys);
    }

    private static String _createKey(String keyHead, String splice, String[] keys) {
        StringBuilder b = new StringBuilder(keyHead);
        for (String key : keys) {
            b.append(key);
            b.append(splice);
        }
        return b.toString();
    }

}
