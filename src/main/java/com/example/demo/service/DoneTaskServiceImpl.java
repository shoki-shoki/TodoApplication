package com.example.demo.service;

import com.example.demo.entity.DoneTask;
import com.example.demo.repository.DoneTaskRepository;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * タスク関連のビジネスロジックを担当するサービスクラスです。
 * タスクの検索、保存、更新、削除、およびソート機能を提供します。
 */
@Service
public class DoneTaskServiceImpl implements DoneTaskService {

    @Autowired
    DoneTaskRepository doneTaskRepository;

    /**
     * タスク一覧を取得（ソート機能付き）。
     * @param sortColumn ソート対象のカラム名（例：priority, deadline, title, status）
     * @param sortOrder ソート順（ASC or DESC）
     * @return ソート適用済みのタスクリスト
     */
    @Override
    public List<DoneTask> findAllSortedDoneTask(String sortColumn, String sortOrder) {
        List<String> validColumns = List.of("title", "description", "deadline", "priority", "status");

        // 不正なカラム名を防ぐ
        if (!validColumns.contains(sortColumn)) {
            sortColumn = "deadline"; // デフォルトのソートカラム
        }

        return doneTaskRepository.findAllSortedDoneTask(sortColumn, sortOrder);
    }

    

    @Override
    public void restoreToTask(int taskId) {
    	doneTaskRepository.restoreToTask(taskId);
    }
}