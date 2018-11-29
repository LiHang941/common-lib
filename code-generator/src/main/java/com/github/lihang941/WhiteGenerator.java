package com.github.lihang941;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : lihang941
 * @since : 2018/11/29
 */
public abstract class WhiteGenerator implements Generator {

    public Set<String> whiteSet = new HashSet<>();

    public boolean isWhite(String name) {
        return whiteSet.contains(name);
    }

}
