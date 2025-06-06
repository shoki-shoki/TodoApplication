package com.example.demo.controller;

import com.example.demo.entity.Task;
import com.example.demo.form.TaskForm;
import com.example.demo.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Webアプリケーションのタスク関連機能を担当するControllerクラスです。
 * タスクの一覧表示、登録、変更、ソート機能などの機能が含まれています。
 */
@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * タスクの一覧を表示するメソッド（ソート機能付き）。
     * 
     * @param sortColumn ソート対象のカラム名（例：priority, deadline）
     * @param sortOrder ソート順（ASC or DESC）
     * @param model タスク一覧をViewに渡すためのSpringのModelオブジェクト
     * @return "task/index" - タスク一覧表示用のHTMLテンプレートのパス
     */
    @GetMapping("/task/list")
    public String showTaskList(@RequestParam(required = false, defaultValue = "deadline") String sortColumn,
                               @RequestParam(required = false, defaultValue = "ASC") String sortOrder,
                               Model model) {
        // 対応するカラムのリスト (セキュリティのためホワイトリスト化)
        List<String> validColumns = List.of("title", "description", "deadline", "priority", "status");

        // 不正なカラム名を防ぐ
        if (!validColumns.contains(sortColumn)) {
            sortColumn = "deadline"; // デフォルトのソートカラム
        }

        // タスク一覧を取得 (ソート適用)
        List<Task> taskList = taskService.findAllSorted(sortColumn, sortOrder);

        model.addAttribute("taskList", taskList);
        model.addAttribute("sortColumn", sortColumn);
        model.addAttribute("sortOrder", sortOrder);

        return "task/index";
    }


    /**
     * タスクの新規登録画面を表示するメソッドです。
     * @param model タスクフォームをViewに渡すためのSpringのModelオブジェクト
     * @return "task/edit" - タスク新規登録画面のHTMLテンプレートのパス
     */
    @GetMapping(value = "/task/add")
    public String showForm(Model model) {
        TaskForm taskForm = new TaskForm();
        model.addAttribute("taskForm", taskForm);
        return "task/edit";
    }

    /**
     * タスクの変更画面を表示するメソッドです。
     */
    @GetMapping(value = "/task/edit")
    public String showEditForm(@RequestParam("taskId") int taskId, Model model) {
        TaskForm taskForm = taskService.getTask(taskId);
        model.addAttribute("taskForm", taskForm);
        return "task/edit";
    }

    /**
     * タスクの確認画面を表示するメソッドです。
     */
    @GetMapping(value = "/task/confirm")
    public String showConfirmForm(@Validated TaskForm taskForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "task/edit";
        }
        model.addAttribute("taskForm", taskForm);
        return "task/confirm";
    }

    /**
     * タスクを保存するメソッドです。
     */
    @PostMapping(value = "/task/save")
    public String saveTask(@Validated TaskForm taskForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return "task/edit";
        }
        String completeMessage = taskService.save(taskForm);
        redirectAttributes.addFlashAttribute("completeMessage", completeMessage);
        return "redirect:/task/complete";
    }

    /**
     * タスク完了画面を表示するメソッドです。
     */
    @GetMapping("/task/complete")
    public String showCompletePage() {
        return "task/complete";
    }

    /**
     * タスクの削除確認画面を表示するメソッドです。
     */
    @GetMapping(value = "/task/delete")
    public String showDeleteForm(@RequestParam("taskId") int taskId, Model model) {
        TaskForm taskForm = taskService.getTask(taskId);
        model.addAttribute("taskForm", taskForm);
        return "task/deleteConfirm";
    }

    /**
     * タスクを削除するメソッドです。
     */
    @PostMapping(value = "/task/delete")
    public String deleteTask(@RequestParam("taskId") int taskId, RedirectAttributes redirectAttributes, Model model) {
        String completeMessage = taskService.delete(taskId);
        redirectAttributes.addFlashAttribute("completeMessage", completeMessage);
        return "redirect:/task/complete";
    }

    /**
     * タスクの確認画面から変更画面に戻るメソッドです。
     */
    @GetMapping("/task/back")
    public String backToEditPage(TaskForm taskForm, Model model) {
        model.addAttribute("taskForm", taskForm);
        return "task/edit";
    }
    
    /**完了済タスクを表示*/
    
    @GetMapping("/task/donetask")
    public String showDoneTaskList(@RequestParam(required = false, defaultValue = "title") String sortColumn,
                               @RequestParam(required = false, defaultValue = "DESC") String sortOrder,
                               Model model) {
        // 対応するカラムのリスト (セキュリティのためホワイトリスト化)
        List<String> validColumns = List.of("title", "description", "deadline", "priority", "status");

        // 不正なカラム名を防ぐ
        if (!validColumns.contains(sortColumn)) {
            sortColumn = "priority"; // デフォルトのソートカラム
        }

        // タスク一覧を取得 (ソート適用)
        List<Task> taskList = taskService.findAllSorted(sortColumn, sortOrder);

        model.addAttribute("taskList", taskList);
        model.addAttribute("sortColumn", sortColumn);
        model.addAttribute("sortOrder", sortOrder);

        return "task/donetask";
    }
    	
    
}
