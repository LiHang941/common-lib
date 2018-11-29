package com.github.lihang941.example.convert;

import java.util.function.Function;
import com.github.lihang941.common.bean.BeanConvert;
import com.github.lihang941.example.dto.UserDto;
import com.github.lihang941.example.entity.User;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018-11-29 17:54
 */

public class UserConvert extends BeanConvert {

    public static Function<User, UserDto> toUserDto() {
        return user ->
                copy(user, UserDto.class);
    }

    public static Function<UserDto,User> toUser() {
        return userDto ->
                copy(userDto, User.class);
    }

}
