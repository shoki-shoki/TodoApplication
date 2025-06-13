package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.DoneTask;
import com.example.demo.mapper.DoneTaskMapper;

@Repository
public class DoneTaskRepository {
	
	private final DoneTaskMapper doneTaskMapper;
	
    public DoneTaskRepository(DoneTaskMapper doneTaskMapper) {
        this.doneTaskMapper = doneTaskMapper;
        }
    
    public List<DoneTask> findAll() {
            return doneTaskMapper.findAll();
       	}

    public List<DoneTask> findAllSortedDoneTask(String sortColumn, String sortOrder) {
        return doneTaskMapper.findAllSortedDoneTask(sortColumn, sortOrder);
    }
    
    
    public void restoreToTask(int taskId) {
        doneTaskMapper.insertToTask(taskId); 
        doneTaskMapper.delete(taskId);
    }

    
    
}