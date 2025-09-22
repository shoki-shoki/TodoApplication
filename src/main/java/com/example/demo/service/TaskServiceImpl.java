package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.form.TaskForm;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
タスク関連のビジネスロジックを担当するサービスクラス。
*/


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;		//taskRepositoryをこのクラスで使えるよにする

    
    
    // タスク一覧を取得するメソッド（ソート機能付き）。

    @Override
    public List<Task> findAllSorted(String sortColumn, String sortOrder) {		//String sortColumn, String sortOrderの値を外から受け取り、このメソッド内で使えるようにする
        List<String> validColumns = List.of("title", "description", "deadline", "priority", "status");		//有効な項目名（カラム名）をリストにまとめる(不正カラム✓のため)

        if (!validColumns.contains(sortColumn)) {		//ソート項目に有効なカラム名でないものが指定されたら、deadlineで強制ソート
            sortColumn = "deadline";
        }

        List<Task> taskList = taskRepository.findAllSorted(sortColumn, sortOrder);		//タスクを1つずつ取得し、Task型のtaskListに格納。Task型(entity)を使うことで、DBから得た値をjavaでそのまま扱える

        // ✅ `status == 3` のタスクを `donetask` に移動＆削除！
        for (Task task : taskList) {		//taskListの値を1つずつ、変数taskに格納
            if (task.getStatus() == 3) {	//もしtaskのステータスが3だったら下記の処理をする
                taskRepository.moveToDoneTask(task.getTaskId()); // データをdonetaskてへINSERT
                taskRepository.delete(task.getTaskId());          // データをtaskテーブルから削除
            }
        }

        return taskRepository.findAllSorted(sortColumn, sortOrder);		//ソート済のタスク一覧の値を返す
    }
        


    // 指定されたIDのタスクを取得するメソッド
    
    @Override
    public TaskForm getTask(int taskId) {		//引数taskIdで指定されたデータをTaskFrorm型で返すメソッド
        Task task = taskRepository.getTask(taskId);		//指定したtaskIdのデータをTask型の変数taskに格納
        return convertToTaskForm(task);		//taskデータをTaskForm型に変換し、その値を返す
    }

    /**
     * タスクを保存。
     * @param taskForm タスクフォームデータ
     * @return 完了メッセージ
     */
    @Override
    @Transactional
    public String save(TaskForm taskForm) {
        Task task = convertToTask(taskForm);
        String completeMessage;

        if (task.getTaskId() != 0) {
            int updateCount = taskRepository.update(task);
            if (updateCount == 0) {
                throw new OptimisticLockingFailureException("楽観ロックエラー");
            }
            completeMessage = "It's done!";
        } else {
            taskRepository.save(task);
            completeMessage = "It's done!";
        }
        return completeMessage;
    }

    /**
     * タスクを削除。
     * @param taskId タスクID
     * @return 完了メッセージ
     */
    @Override
    @Transactional
    public String delete(int taskId) {
        taskRepository.delete(taskId);
        return "It's done!";
    }

    /**
     * タスクフォームをタスクエンティティに変換するメソッド。
     */
    @Override
    public Task convertToTask(TaskForm taskForm) {
        Task task = new Task();
        task.setTaskId(taskForm.getTaskId());
        task.setTitle(taskForm.getTitle());
        task.setDescription(taskForm.getDescription());
        task.setDeadline(taskForm.getDeadline());
        task.setPriority(taskForm.getPriority());
        task.setStatus(taskForm.getStatus());
        task.setUpdatedAt(taskForm.getUpdatedAt());
        return task;
    }

    /**
     * タスクエンティティをタスクフォームに変換するメソッド。
     */
    @Override
    public TaskForm convertToTaskForm(Task task) {
        TaskForm taskForm = new TaskForm();
        taskForm.setTaskId(task.getTaskId());
        taskForm.setTitle(task.getTitle());
        taskForm.setDescription(task.getDescription());
        taskForm.setDeadline(task.getDeadline());
        taskForm.setPriority(task.getPriority());
        taskForm.setStatus(task.getStatus());
        taskForm.setUpdatedAt(task.getUpdatedAt());
        return taskForm;
    }

    
}