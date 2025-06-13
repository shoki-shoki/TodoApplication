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
    List<DoneTask> findAllSortedDoneTask(@Param("sortColumn") String sortColumn, @Param("sortOrder") String sortOrder);
    
    

    /** 
     * 完了済みタスクを未完了タスクに戻します。
     * @param taskId 戻すタスクID
     */
    void insertToTask(@Param("taskId") int taskId);

    int delete(int taskId);
    
    
}