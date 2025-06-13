package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.DoneTask;

/**
 * タスク関連のサービスを提供するインターフェースです。
 */
public interface DoneTaskService {
    

    /** ソート機能付きのタスク一覧を取得します。 */
    List<DoneTask> findAllSortedDoneTask(String sortColumn, String sortOrder);
    
    void restoreToTask(int taskId);
}