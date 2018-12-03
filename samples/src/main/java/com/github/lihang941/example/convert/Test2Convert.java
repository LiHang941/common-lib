package com.github.lihang941.example.convert;

import java.util.function.Function;
import com.github.lihang941.common.bean.BeanConvert;
import com.github.lihang941.example.dto.Test2Dto;
import com.github.lihang941.example.entity.Test2;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018-11-30 18:02
 */

public class Test2Convert extends BeanConvert {

    public static Function<Test2, Test2Dto> toTest2Dto() {
        return test2 ->
                copy(test2, Test2Dto.class);
    }

    public static Function<Test2Dto,Test2> toTest2() {
        return test2Dto ->
                copy(test2Dto, Test2.class);
    }

}
