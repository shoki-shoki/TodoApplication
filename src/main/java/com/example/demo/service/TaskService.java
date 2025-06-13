package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.Task;
import com.example.demo.form.TaskForm;

/**
 * タスク関連のサービスを提供するインターフェースです。
 */
public interface TaskService {

    /** ソート機能付きのタスク一覧を取得します。 */
    List<Task> findAllSorted(String sortColumn, String sortOrder);

    /** 指定されたタスクIDに対応するタスクを取得します。 */
    TaskForm getTask(int taskId);

    /** タスクを保存します。 */
    String save(TaskForm taskForm);

    /** タスクを削除します。 */
    String delete(int taskId);

    /** タスクのフォームデータをタスクエンティティに変換します。 */
    Task convertToTask(TaskForm taskForm);

    /** タスクエンティティをタスクのフォームデータに変換します。 */
    TaskForm convertToTaskForm(Task task);

}