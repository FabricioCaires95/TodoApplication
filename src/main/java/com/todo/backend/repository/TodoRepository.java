package com.todo.backend.repository;

import com.todo.backend.domain.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    Page<Todo> findAllByIsFinished(Pageable pageable, boolean isFinished);

    List<Todo> getTodosByUserIdAndIsFinished(Long userId, boolean isFinished);

}
