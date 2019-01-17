package com.github.lihang941.example.dto;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018-12-04 15:11
 */
@Accessors(chain = true)
@Data
public class Test2Dto {
    private Long id;
    private String testName;
}
