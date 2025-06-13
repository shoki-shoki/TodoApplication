package com.example.demo.controller;

import com.example.demo.entity.DoneTask;
import com.example.demo.service.DoneTaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Webアプリケーションのタスク関連機能を担当するControllerクラスです。
 * タスクの一覧表示、登録、変更、ソート機能などの機能が含まれています。
 */
@Controller
public class DoneTaskController {

	@Autowired
    private final DoneTaskService doneTaskService;

    public DoneTaskController(DoneTaskService doneTaskService) {
        this.doneTaskService = doneTaskService;
    }

    /**
     * タスクの一覧を表示するメソッド（ソート機能付き）。
     * 
     * @param sortColumn ソート対象のカラム名（例：priority, deadline）
     * @param sortOrder ソート順（ASC or DESC）
     * @param model タスク一覧をViewに渡すためのSpringのModelオブジェクト
     * @return "task/index" - タスク一覧表示用のHTMLテンプレートのパス
     */
    @GetMapping("/donetask/list")
    public String showDoneTaskList(@RequestParam(required = false, defaultValue = "deadline") String sortColumn,
                               @RequestParam(required = false, defaultValue = "ASC") String sortOrder,
                               Model model) {
        // 対応するカラムのリスト (セキュリティのためホワイトリスト化)
        List<String> validColumns = List.of("title", "description", "deadline", "priority", "status");

        // 不正なカラム名を防ぐ
        if (!validColumns.contains(sortColumn)) {
            sortColumn = "deadline"; // デフォルトのソートカラム
        }

        // タスク一覧を取得 (ソート適用)
        List<DoneTask> doneTaskList = doneTaskService.findAllSortedDoneTask(sortColumn, sortOrder);

        model.addAttribute("doneTaskList", doneTaskList);
        model.addAttribute("sortColumn", sortColumn);
        model.addAttribute("sortOrder", sortOrder);

        return "task/donetask";
    }

    
    @GetMapping("/donetask/restore/{taskId}")
    public String restoreToTask(@PathVariable int taskId, 
                                @RequestParam(required = false, defaultValue = "deadline") String sortColumn,
                                @RequestParam(required = false, defaultValue = "ASC") String sortOrder, 
                                Model model) {
        doneTaskService.restoreToTask(taskId); // タスクを復元
        List<DoneTask> updatedDoneTaskList = doneTaskService.findAllSortedDoneTask(sortColumn, sortOrder);
        model.addAttribute("doneTaskList", updatedDoneTaskList);
        model.addAttribute("sortColumn", sortColumn);
        model.addAttribute("sortOrder", sortOrder);
        return "task/donetask"; // テンプレート名。実際のファイルパスに合わせてください
    }
    	
    	
















}
