package com.github.lihang941.example.convert;

import java.util.function.Function;
import com.github.lihang941.common.bean.BeanConvert;
import com.github.lihang941.example.dto.TestDto;
import com.github.lihang941.example.entity.Test;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018-11-29 17:54
 */

public class TestConvert extends BeanConvert {

    public static Function<Test, TestDto> toTestDto() {
        return test ->
                copy(test, TestDto.class);
    }

    public static Function<TestDto,Test> toTest() {
        return testDto ->
                copy(testDto, Test.class);
    }

}
