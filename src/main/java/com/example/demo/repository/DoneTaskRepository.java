package com.example.demo.repository;

import com.example.demo.entity.DoneTask;
import com.example.demo.mapper.DoneTaskMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 完了済みタスク情報にアクセスするためのリポジトリクラスです。
 */
@Repository
public class DoneTaskRepository {

    private final DoneTaskMapper doneTaskMapper;

    /**
     * コンストラクタ
     * @param doneTaskMapper 完了済みタスクデータへのマッパー
     */
    public DoneTaskRepository(DoneTaskMapper doneTaskMapper) {
        this.doneTaskMapper = doneTaskMapper;
    }

    /**
     * 全ての完了済みタスクを取得します。
     * @return 完了済みタスクのリスト
     */
    public List<DoneTask> findAll() {
        return doneTaskMapper.findAll();
    }

    /**
     * ソート機能付きの完了済みタスク一覧を取得します。
     * @param sortColumn ソート対象のカラム名（例：priority, deadline, title, status）
     * @param sortOrder ソート順（ASC or DESC）
     * @return ソート適用済みの完了済みタスク一覧
     */
    public List<DoneTask> findAllSorted(String sortColumn, String sortOrder) {
        return doneTaskMapper.findAllSorted(sortColumn, sortOrder);
    }

    /**
     * 指定された完了済みタスクIDに対応する完了済みタスクを取得します。
     * @param taskId タスクID
     * @return 完了済みタスク
     */
    public DoneTask getTask(int taskId) {
        return doneTaskMapper.getTask(taskId);
    }

    /**
     * 完了済みタスクを保存します。
     * @param doneTask 保存する完了済みタスク
     */
    public void save(DoneTask doneTask) {
        doneTaskMapper.save(doneTask);
    }

    /**
     * 完了済みタスクを更新します。
     * @param doneTask 更新する完了済みタスク
     * @return 更新された行数
     */
    public int update(DoneTask doneTask) {
        return doneTaskMapper.update(doneTask);
    }

    /**
     * 完了済みタスクを削除します。
     * @param taskId 削除する完了済みタスクID
     * @return 削除された行数
     */
    public int delete(int taskId) {
        return doneTaskMapper.delete(taskId);
    }
}