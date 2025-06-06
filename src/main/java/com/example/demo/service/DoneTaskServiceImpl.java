package com.example.demo.service;

import com.example.demo.entity.DoneTask;
import com.example.demo.form.DoneTaskForm;
import com.example.demo.repository.DoneTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 完了済みタスク関連のビジネスロジックを担当するサービスクラスです。
 * 完了済みタスクの検索、保存、更新、削除、およびソート機能を提供します。
 */
@Service
public class DoneTaskServiceImpl implements DoneTaskService {

    @Autowired
    DoneTaskRepository doneTaskRepository;

    /**
     * 完了済みタスク一覧を取得（ソート機能付き）。
     * @param sortColumn ソート対象のカラム名（例：priority, deadline, title, status）
     * @param sortOrder ソート順（ASC or DESC）
     * @return ソート適用済みの完了済みタスクリスト
     */
    @Override
    public List<DoneTask> findAllSorted(String sortColumn, String sortOrder) {
        return doneTaskRepository.findAllSorted(sortColumn, sortOrder);
    }

    /** 
     * 完了済みタスク一覧を取得。
     * @return 全完了済みタスクのリスト
     */
    @Override
    public List<DoneTask> findAll() {
        return doneTaskRepository.findAll();
    }

    /**
     * 指定されたIDの完了済みタスクを取得。
     * @param taskId タスクID
     * @return 完了済みタスク情報
     */
    @Override
    public DoneTaskForm getDoneTask(int taskId) {
        DoneTask doneTask = doneTaskRepository.getTask(taskId);
        return convertToDoneTaskForm(doneTask);
    }

    /**
     * 完了済みタスクを保存。
     * @param doneTaskForm 完了済みタスクフォームデータ
     * @return 完了メッセージ
     */
    @Override
    @Transactional
    public String save(DoneTaskForm doneTaskForm) {
        DoneTask doneTask = convertToDoneTask(doneTaskForm);
        String completeMessage;

        if (doneTask.getTaskId() != 0) {
            int updateCount = doneTaskRepository.update(doneTask);
            if (updateCount == 0) {
                throw new OptimisticLockingFailureException("楽観ロックエラー");
            }
            completeMessage = "完了済みタスクを更新しました！";
        } else {
            doneTaskRepository.save(doneTask);
            completeMessage = "完了済みタスクを追加しました！";
        }
        return completeMessage;
    }

    /**
     * 完了済みタスクを削除。
     * @param taskId タスクID
     * @return 完了メッセージ
     */
    @Override
    @Transactional
    public String delete(int taskId) {
        doneTaskRepository.delete(taskId);
        return "完了済みタスクを削除しました！";
    }

    /**
     * 完了済みタスクフォームを完了済みタスクエンティティに変換するメソッド。
     */
    @Override
    public DoneTask convertToDoneTask(DoneTaskForm doneTaskForm) {
        DoneTask doneTask = new DoneTask();
        doneTask.setTaskId(doneTaskForm.getTaskId());
        doneTask.setTitle(doneTaskForm.getTitle());
        doneTask.setDescription(doneTaskForm.getDescription());
        doneTask.setDeadline(doneTaskForm.getDeadline());
        doneTask.setPriority(doneTaskForm.getPriority());
        doneTask.setStatus(doneTaskForm.getStatus());
        doneTask.setUpdatedAt(doneTaskForm.getUpdatedAt());
        return doneTask;
    }

    /** 
     * 完了済みタスクエンティティを完了済みタスクフォームに変換するメソッド。 
     */
    @Override
    public DoneTaskForm convertToDoneTaskForm(DoneTask doneTask) {
        DoneTaskForm doneTaskForm = new DoneTaskForm();
        doneTaskForm.setTaskId(doneTask.getTaskId());
        doneTaskForm.setTitle(doneTask.getTitle());
        doneTaskForm.setDescription(doneTask.getDescription());
        doneTaskForm.setDeadline(doneTask.getDeadline());
        doneTaskForm.setPriority(doneTask.getPriority());
        doneTaskForm.setStatus(doneTask.getStatus());
        doneTaskForm.setUpdatedAt(doneTask.getUpdatedAt());
        return doneTaskForm;
    }
}