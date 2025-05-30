package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.Constants;
import com.example.demo.entity.Task;
import com.example.demo.form.TaskForm;
import com.example.demo.repository.TaskRepository;

/**
 * タスク関連のビジネスロジックを担当するサービスクラスです。
 * タスクの検索、保存、更新などの機能を提供します。
 */
@Service
public class TaskServiceImpl implements TaskService{

	@Autowired
	TaskRepository taskRepository;
	
	/**
	 * タスク一覧を取得するメソッドです。
	 *
	 * @return List<Task> タスク一覧。
	 */
	@Override
	public List<Task> findAll() {
		return taskRepository.findAll();
		}
	
	
	@Override
	@Transactional
	public String save(TaskForm taskForm) {
		
		//変換処理
		Task task = convertToTask(taskForm);
		
		//登録処理の場合
		taskRepository.save(task);
		
		//完了メッセージをセット
		String completeMessage = Constants.REGISTER_COMPLETE;
		return completeMessage;
	}
	
	
	@Override
	public Task convertToTask(TaskForm taskForm) {
	    Task task = new Task();
	    task.setTaskId(taskForm.getTaskId());
	    task.setTitle(taskForm.getTitle());
	    task.setDescription(taskForm.getDescription());
	    task.setDeadline(taskForm.getDeadline());
	    task.setStatus(taskForm.getStatus());
	    task.setUpdatedAt(taskForm.getUpdatedAt());
	    return task;
	    }

}
