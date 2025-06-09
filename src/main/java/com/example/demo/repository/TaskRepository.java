package com.example.demo.repository;

import com.example.demo.entity.Task;
import com.example.demo.mapper.TaskMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * タスク情報にアクセスするためのリポジトリクラスです。
 */
@Repository
public class TaskRepository {

    private final TaskMapper taskMapper;

    /**
     * コンストラクタ
     * @param taskMapper タスクデータへのマッパー
     */
    public TaskRepository(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    /**
     * 全てのタスクを取得します。
     * @return タスクのリスト
     */
    public List<Task> findAll() {
        return taskMapper.findAll();
    }

    /**
     * ソート機能付きのタスク一覧を取得します。
     * @param sortColumn ソート対象のカラム名（例：priority, deadline, title, status）
     * @param sortOrder ソート順（ASC or DESC）
     * @return ソート適用済みのタスク一覧
     */
    public List<Task> findAllSorted(String sortColumn, String sortOrder) {
        return taskMapper.findAllSorted(sortColumn, sortOrder);
    }

    /**
     * 指定されたタスクIDに対応するタスクを取得します。
     * @param taskId タスクID
     * @return タスク
     */
    public Task getTask(int taskId) {
        return taskMapper.getTask(taskId);
    }

    /**
     * タスクを保存します。
     * @param task 保存するタスク
     */
    public void save(Task task) {
        taskMapper.save(task);
    }

    /**
     * タスクを更新します。 
     * @param task 更新するタスク
     * @return 更新された行数
     */
    public int update(Task task) {
        return taskMapper.update(task);
    }

    /**
     * タスクを削除します。
     * @param taskId 削除するタスクID
     * @return 削除された行数
     */
    public int delete(int taskId) {
        return taskMapper.delete(taskId);
    }
}