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
 * タスク関連のビジネスロジックを担当するサービスクラスです。
 * タスクの検索、保存、更新、削除、およびソート機能を提供します。
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    /**
     * タスク一覧を取得（ソート機能付き）。
     * @param sortColumn ソート対象のカラム名（例：priority, deadline, title, status）
     * @param sortOrder ソート順（ASC or DESC）
     * @return ソート適用済みのタスクリスト
     */
    @Override
    public List<Task> findAllSorted(String sortColumn, String sortOrder) {
        List<String> validColumns = List.of("title", "description", "deadline", "priority", "status");

        // 不正なカラム名を防ぐ
        if (!validColumns.contains(sortColumn)) {
            sortColumn = "deadline";
        }

        List<Task> taskList = taskRepository.findAllSorted(sortColumn, sortOrder);

        // ✅ `status == 3` のタスクを `donetask` に移動＆削除！
        for (Task task : taskList) {
            if (task.getStatus() == 3) {
                taskRepository.moveToDoneTask(task.getTaskId()); // donetaskへINSERT
                taskRepository.delete(task.getTaskId());          // taskテーブルから削除
            }
        }

        return taskRepository.findAllSorted(sortColumn, sortOrder); //
    }
        


    /**
     * 指定されたIDのタスクを取得。
     * @param taskId タスクID
     * @return タスク情報
     */
    @Override
    public TaskForm getTask(int taskId) {
        Task task = taskRepository.getTask(taskId);
        return convertToTaskForm(task);
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
            completeMessage = "タスクを更新しました！";
        } else {
            taskRepository.save(task);
            completeMessage = "タスクを追加しました！";
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
        return "タスクを削除しました！";
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