package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;


@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    
	@RequestMapping(value = "/task/list", method = RequestMethod.GET)
	public String showTask(Model model) {
		
		//タスクの一覧を取得
		List<Task> taskList = taskService.findAll();		
		model.addAttribute("taskList", taskList);
		
		return "task/index";
	}
	
}


