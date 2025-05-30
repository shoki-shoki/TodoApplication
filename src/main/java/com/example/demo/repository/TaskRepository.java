package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Task;
import com.example.demo.mapper.TaskMapper;


/**
 * タスク情報にアクセスするためのリポジトリクラスです。
 */
@Repository
public class TaskRepository {
	
	private final TaskMapper taskMapper;
	
    /**
     * コンストラクタ
     *
     * @param taskMapper タスクデータへのマッパー
     */
    public TaskRepository(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    /**
     * 全てのタスクを取得します。
     *
     * @return タスクのリスト
     */
    public List<Task> findAll() {
        return taskMapper.findAll();
    }
    
    /**
     * タスクを保存します。
     *
     * @param task 保存するタスク
     */
    public void save(Task task) {
        taskMapper.save(task);
    }

}

