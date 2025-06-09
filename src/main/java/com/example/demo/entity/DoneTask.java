package com.example.demo.entity;

import java.time.LocalDateTime;

/**
 * 完了済みタスクのエンティティクラス
 */
public class DoneTask {
    /** タスクID（自動インクリメントされる一意の識別子）。 */
    private int taskId;

    /** タスクのタイトル。 */
    private String title;

    /** タスクの説明。 */
    private String description;

    /** タスクの完了日時。 */
    private LocalDateTime completedAt;

    /** タスク優先度（1〜3）。 */
    private int priority;

    /** ユーザーID（タスクを所有するユーザーの識別子）。 */
    private Integer userId;

    /** タスクの作成日時（デフォルトは現在の日時）。 */
    private LocalDateTime createdAt;

    /** タスクの更新日時（更新時に現在の日時に自動設定）。 */
    private LocalDateTime updatedAt;

    /** 削除フラグ（タスクが削除されたかどうかを示す）。 */
    private Boolean deleteFlg;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getDeleteFlg() {
        return deleteFlg;
    }

    public void setDeleteFlg(Boolean deleteFlg) {
        this.deleteFlg = deleteFlg;
    }
}