package com.todo.backend.mapper;

import com.todo.backend.domain.User;
import com.todo.backend.dto.UserDto;
import org.mapstruct.factory.Mappers;
import org.testng.annotations.Test;

import static com.todo.backend.utils.UserUtils.getUserDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserMapperTest {

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void testDtoToEntity() {
        UserDto userDto = getUserDto();
        User user = userMapper.dtoToEntity(userDto);
        assertNotNull(user);
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getPassword(), userDto.getPassword());
    }


}
