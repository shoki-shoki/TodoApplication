package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.DoneTask;
import com.example.demo.form.DoneTaskForm;

/**
 * 完了済みタスク関連のサービスを提供するインターフェースです。
 */
public interface DoneTaskService {
    
    /** すべての完了済みタスクを取得します。 */
    List<DoneTask> findAll();

    /** ソート機能付きの完了済みタスク一覧を取得します。 */
    List<DoneTask> findAllSorted(String sortColumn, String sortOrder);

    /** 指定されたタスクIDに対応する完了済みタスクを取得します。 */
    DoneTaskForm getDoneTask(int taskId);

    /** 完了済みタスクを保存します。 */
    String save(DoneTaskForm doneTaskForm);

    /** 完了済みタスクを削除します。 */
    String delete(int taskId);

    /** 完了済みタスクのフォームデータを完了済みタスクエンティティに変換します。 */
    DoneTask convertToDoneTask(DoneTaskForm doneTaskForm);

    /** 完了済みタスクエンティティを完了済みタスクのフォームデータに変換します。 */
    DoneTaskForm convertToDoneTaskForm(DoneTask doneTask);
}