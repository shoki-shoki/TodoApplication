package com.example.demo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.demo.entity.DoneTask;

/**
 * タスクエンティティにアクセスするための MyBatis マッパーインターフェースです。
 */
@Mapper
public interface DoneTaskMapper {
    
    /** 
     * 全てのタスクを取得します。 
     * @return タスクのリスト 
     */ 
    List<DoneTask> findAll();

    /** 
     * ソート機能付きのタスク一覧を取得します。 
     * @param sortColumn ソート対象のカラム名（例：priority, deadline, title, status） 
     * @param sortOrder ソート順（ASC or DESC） 
     * @return ソート適用済みのタスク一覧 
     */ 
    List<DoneTask> findAllSorted(@Param("sortColumn") String sortColumn, @Param("sortOrder") String sortOrder);

    /** 
     * タスクを保存します。 
     * @param task 保存するタスク 
     */ 
    void save(DoneTask task);

    /** 
     * 指定されたタスクIDに対応するタスクを取得します。 
     * @param taskId タスクID 
     * @return タスク 
     */ 
    DoneTask getTask(int taskId);

    /** 
     * タスクを更新します。 
     * @param task 更新するタスク 
     * @return 更新された行数 
     */ 
    int update(DoneTask task);

    /** 
     * タスクを削除します。 
     * @param taskId 削除するタスクID 
     * @return 削除された行数 
     */ 
    int delete(int taskId);
}