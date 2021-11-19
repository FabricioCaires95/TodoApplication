package com.todo.backend.utils;

import com.todo.backend.domain.User;
import com.todo.backend.dto.UserDto;

public class UserUtils {


    public static User getUserEntity() {
        return User.builder()
                .name("spacer")
                .email("spacer@gmail.com")
                .password("sdadasdsada")
                .build();
    }


    public static UserDto getUserDto() {
        return UserDto.builder()
                .name("legend")
                .email("legend123@gmail.com")
                .password("asdadadafasfas")
                .build();
    }
}
