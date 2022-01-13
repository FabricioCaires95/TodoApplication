package com.todo.backend.mapper;


import com.todo.backend.domain.Todo;
import com.todo.backend.dto.TodoDto;
import com.todo.backend.dto.TodoUpdateDto;
import org.mapstruct.factory.Mappers;
import org.testng.annotations.Test;

import static com.todo.backend.utils.TodoUtils.getTodoDto;
import static com.todo.backend.utils.TodoUtils.getTodoEntity;
import static com.todo.backend.utils.TodoUtils.getUpdateTodoDtoWithComplet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TodoMapperTest {


    private TodoMapper todoMapper = Mappers.getMapper(TodoMapper.class);

    @Test
    public void shoudConvertToDto() {
        Todo entity = getTodoEntity();
        TodoDto dto = todoMapper.convertToDto(entity);

        assertNotNull(dto);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTitle(), entity.getTitle());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getDeadline(), entity.getDeadline());
    }

    @Test
    public void shoudConvertToEntity() {
        TodoDto dto = getTodoDto();
        Todo entity = todoMapper.convertToEntity(dto);

        assertNotNull(entity);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getDeadline(), dto.getDeadline());
    }

    @Test
    public void shouldConvertUpdateDtoToEntity() {
        TodoUpdateDto updateDto = getUpdateTodoDtoWithComplet();
        Todo entity = todoMapper.convertUpdateDtoToEntity(updateDto);

        assertNotNull(entity);
        assertEquals(entity.getId(), updateDto.getId());
        assertEquals(entity.getTitle(), updateDto.getTitle());
        assertEquals(entity.getDescription(), updateDto.getDescription());
        assertEquals(entity.getDeadline(), updateDto.getDeadline());
    }


}
