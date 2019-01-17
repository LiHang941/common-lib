package com.github.lihang941.example.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018-12-04 15:11
 */
@Accessors(chain = true)
@Data
public class UserDto {
    private String userId;
    private String userName;
    private Date createTime;
    private Integer userStatus;
}
