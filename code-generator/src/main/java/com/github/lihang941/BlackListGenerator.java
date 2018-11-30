package com.github.lihang941;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 黑名单
 *
 * @author : lihang941
 * @since : 2018/11/29
 */
public abstract class BlackListGenerator implements Generator {

    private List<String> blackNameList = new ArrayList<>();
    private List<String> blackRegList = new ArrayList<>();

    public boolean isBlackList(String name) {
        if (blackNameList.contains(name)) {
            return true;
        }
        for (String reg : blackRegList) {
            if (Pattern.compile(reg).matcher(name).find()) {
                return true;
            }
        }
        return false;
    }

    public void addName(String name) {
        blackNameList.add(name);
    }

    public void addReg(String reg) {
        blackRegList.add(reg);
    }

    public List<String> getBlackNameList() {
        return blackNameList;
    }

    public List<String> getBlackRegList() {
        return blackRegList;
    }
}
