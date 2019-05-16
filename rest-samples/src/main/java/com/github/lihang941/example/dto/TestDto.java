package com.github.lihang941.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author : lihang1329@gmail.com
 * @since : 2019-05-16 16:46
 */

@Getter
@Setter
@ToString
@Accessors(chain = true) 
public class TestDto {
    private Long id;
    private String testName;
}
