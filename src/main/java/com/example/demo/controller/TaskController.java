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
 * Webアプリケーションのタスク関連機能を担当するControllerクラス。
 */
@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    
    
    
    //タスクの一覧を表示するメソッド（ソート機能付き）。
    
    @GetMapping("/task/list")		//task/listリンクへのアクセスがあった際に実行
    public String showTaskList(@RequestParam(required = false, defaultValue = "deadline") String sortColumn,		//URL内のsortColumnの値を変数sortColumnに入れる	※メソッドの戻り値の型をstring型にすることで、View(文字の表示ページ)を返せるようにしている
                               @RequestParam(required = false, defaultValue = "ASC") String sortOrder,				//URL内のsortOrderの値を変数sortOrderに入れる
                               Model model) {																		//model.addAttributeを使えるようにする

        List<String> validColumns = List.of("title", "description", "deadline", "priority", "status");

        // 不正なカラム名を防ぐ
        if (!validColumns.contains(sortColumn)) {
            sortColumn = "deadline"; // デフォルトのソートカラム
        }

        
        List<Task> taskList = taskService.findAllSorted(sortColumn, sortOrder);			//タスクを1つずつ取得し、Task型のtaskListに格納。Task型(entity)を使うことで、DBから得た値をjavaでそのまま扱える

        model.addAttribute("taskList", taskList);		//taskList内の値をtaskListという名前で、task/index内で扱えるようにする
        model.addAttribute("sortColumn", sortColumn);	
        model.addAttribute("sortOrder", sortOrder);		

        return "task/index";	// View名 "task/index" を返す。HTML内では model に追加されたデータ（taskListなど）が使える状態になっている
    }


    
    
    // タスクの新規登録画面を表示するメソッド。
    
    @GetMapping(value = "/task/add")		//task/addリンクへのアクセスがあった際に実行
    public String showForm(Model model) {		//model.addAttributeを使えるようにする
        TaskForm taskForm = new TaskForm();		//TaskFormのデータをtaskForm変数に格納
        model.addAttribute("taskForm", taskForm);		//モデルにtaskFromのデータをtaskFormという名前で格納
        return "task/edit";		//View名　task/edit　を返す。本method内でモデルに格納したデータを扱える
    }

    
    
    // タスクの新規登録画面を表示するメソッド。	
    
    @GetMapping(value = "/task/edit")		//task/editリンクへのアクセスがあった際に実行
    public String showEditForm(@RequestParam("taskId") int taskId, Model model) {		//URL内のtaskIdの値をtaskId変数に格納。モデルを使えるようにする
        TaskForm taskForm = taskService.getTask(taskId);		//tasKForm変数に、指定されたタスクIDのタスク情報を格納
        model.addAttribute("taskForm", taskForm);		//モデルにtaskFormの値を詰める
        return "task/edit";		//View名　task/edit　を返す。
    }

    
    
    //タスクの確認画面を表示するメソッド。

    @GetMapping(value = "/task/confirm")		//task//confirmリンクへのアクセスがあった際に実行
    public String showConfirmForm(@Validated TaskForm taskForm, BindingResult bindingResult, Model model) {		//taskFormの値をバリデーションチェック。結果をbindingResultで保持。モデルを使用できるようにする。
        if (bindingResult.hasErrors()) {		//バリデーションエラーがあった際、task/editを返す。
            return "task/edit";
        }
        model.addAttribute("taskForm", taskForm);		//バリデーションエラーがなかった際、taskFormの値をモデルに格納。
        return "task/confirm";		//View名　task/confirm　を返す。
    }

    
    
    //タスクを保存するメソッド。
    
    @PostMapping(value = "/task/save")		//保存ボタンを押したら動くメソッド。
    public String saveTask(@Validated TaskForm taskForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {		//taskFormのバリデーションチェック＆保持。リダイレクト変数の宣言とモデル使用可にする
        if (bindingResult.hasErrors()) {		//バリデーションチェック
            return "task/edit";
        }
        String completeMessage = taskService.save(taskForm);	//taskFormに入力されたデータをservice.saveでDBに保存する処理を行う。そしてその処理結果をstring型のcompeleteMessageに格納する(taskService.saveで返す値がstring型だから、戻り値の方もstring型)
        redirectAttributes.addFlashAttribute("completeMessage", completeMessage);		//RedirectAttributesが持っているaddFlashAttribute() というメソッドにcompleteMessageの値を「completeMessage」という名前で渡す。
        return "redirect:/task/complete";		//View名　task/complete　へリダイレクト。リダイレクトを使うことで、再読み込みによる二重保存のリスクを防止する。
    }

    
    
    //タスク完了画面を表示するメソッド。
    
    @GetMapping("/task/complete")		//task/completeリンクへのアクセスがあった際に実行
    public String showCompletePage() {		
        return "task/complete";			//View名　task/complet　eを返す。		
    }

    
    
    //タスクの削除確認画面を表示するメソッド。
     
    @GetMapping(value = "/task/delete")		//task/deleteリンクへのアクセスがあった際に実行
    public String showDeleteForm(@RequestParam("taskId") int taskId, Model model) {			//URL内のtaskIdの値をtaskId変数に格納。モデルを使えるようにする
        TaskForm taskForm = taskService.getTask(taskId);		//tasKForm変数に、指定されたタスクIDのタスク情報を格納
        model.addAttribute("taskForm", taskForm);		//モデルにtaskFormの値を詰める
        return "task/deleteConfirm";		//View名　task/deleteConfirm　を返す。	
    }

    
    
    // タスクを削除するメソッド。

    @PostMapping(value = "/task/delete")		//削除ボタンを押したら動くメソッド。
    public String deleteTask(@RequestParam("taskId") int taskId, RedirectAttributes redirectAttributes, Model model) {		//URL内のtaskIdの値をtaskId変数に格納。リダイレクト変数の宣言とモデル使用可にする
        String completeMessage = taskService.delete(taskId);		//指定されたtaskIdのタスクをservice.deleteでDBから削除する処理を行う。そしてその処理結果をstring型のcompeleteMessageに格納する(taskService.saveで返す値がstring型だから、戻り値の方もstring型)
        redirectAttributes.addFlashAttribute("completeMessage", completeMessage);		//RedirectAttributesが持っているaddFlashAttribute() というメソッドにcompleteMessageの値を「completeMessage」という名前で渡す。
        return "redirect:/task/complete";		//View名　task/complete　へリダイレクト。リダイレクトを使うことで、再読み込みによる二重保存のリスクを防止する。
    }

    
    
    // タスクの確認画面から変更画面に戻るメソッドです。

    @GetMapping("/task/back")		//task/backリンクへのアクセスがあった際に実行
    public String backToEditPage(TaskForm taskForm, Model model) {		//taskFormの値およびモデルを使えるようにする
        model.addAttribute("taskForm", taskForm);		//モデルにtaskFormの値を詰める
        return "task/edit";		//View名　task/edit　を返す。
    }
    
    
}
