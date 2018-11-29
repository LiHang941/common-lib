package com.github.lihang941;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/28
 */
public class Context {

    private List<Generator> generatorList;

    public Context() {
        this(new ArrayList<>());
    }

    public Context(List<Generator> generators) {
        if (generators == null) {
            throw new NullPointerException();
        }
        this.generatorList = generators;
    }

    public void generator() {
        for (Generator generator : getGeneratorList()) {
            try {
                generator.generator();
            } catch (Exception e) {
                Generator.LOGGER.warning(generator.getClass() + " 执行失败");
                e.printStackTrace();
            }
        }
    }


    public <T extends Generator> T generator(Class<T> tClass) {
        for (Generator generator : getGeneratorList()) {
            if (generator.getClass() == tClass) {
                return (T)generator;
            }
        }
        return null;
    }


    public List<Generator> getGeneratorList() {
        return generatorList;
    }
}
