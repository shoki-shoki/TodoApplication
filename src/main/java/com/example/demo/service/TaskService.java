package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.Task;
import com.example.demo.form.TaskForm;

/**
 * タスク関連のサービスを提供するインターフェース。
 */
public interface TaskService {


    List<Task> findAllSorted(String sortColumn, String sortOrder);		//タスク一覧を取得するメソッド

    TaskForm getTask(int taskId);		//指定されたIDに対応するデータをDBから取り出し、TaskFromに変換。指定したtaskIDのデータをクラスやviewで扱えるようになる

    String save(TaskForm taskForm);		//taskFormで入力された情報のタスクを保存するメソッド。

    String delete(int taskId);		//指定されたIDのタスクを削除するメソッド

    Task convertToTask(TaskForm taskForm);		//TaskFormのデータをEntityデータに変換。これでHTMLで入力された値をDB用に変換し、DBでの操作で使うことができるようになる

    TaskForm convertToTaskForm(Task task);		//EntityのデータをTaskFormデータに変換。DBのデータをTaskForm型にすることで、それらの値をclassやviewで扱えるようになる

}