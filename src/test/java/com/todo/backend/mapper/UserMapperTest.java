package com.todo.backend.mapper;

import com.todo.backend.domain.User;
import com.todo.backend.dto.UserDto;
import org.mapstruct.factory.Mappers;
import org.testng.annotations.Test;

import static com.todo.backend.utils.UserUtils.getDtoWithTasks;
import static com.todo.backend.utils.UserUtils.getUserWithTasks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserMapperTest {

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void shouldConvertDtoIntoEntity() {
        UserDto userDto = getDtoWithTasks();
        User user = userMapper.mapToEntity(userDto, new CycleAvoidingMappingContext());
        assertNotNull(user);
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getPassword(), userDto.getPassword());
        assertEquals(user.getTasks().size(), 2);
    }

    @Test
    public void shouldConvertEntityIntoDto() {
        User user = getUserWithTasks();
        UserDto userDto = userMapper.mapToDto(user, new CycleAvoidingMappingContext());
        assertNotNull(user);
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertNull(userDto.getPassword());
        assertEquals(user.getTasks().size(), 2);
    }

}
