package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Task;
import com.example.demo.mapper.TaskMapper;


@Repository
public class TaskRepository {
	
	private final TaskMapper taskMapper;
	
    public TaskRepository(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public List<Task> findAll() {
        return taskMapper.findAll();
    }

}

