package com.springapp.todo.controller;

import com.springapp.todo.model.Todo;
import com.springapp.todo.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AppController {

    @Autowired
    private AppService service;

    @GetMapping({"/", "viewTaskList"})
    public String viewAllTasks(Model model, @ModelAttribute("message") String message){
        model.addAttribute("list", service.getAllTasks());
        model.addAttribute("msg", message);
        return "viewTaskList";
    }

    @GetMapping("/updateTaskStatus/{id}")
    public String updateTaskStatus(@PathVariable Long id, RedirectAttributes redirectAttributes){
        if(service.updateStatus(id)){
            redirectAttributes.addFlashAttribute("message", "Update success");
            return "redirect:/viewTaskList";
        }
        redirectAttributes.addFlashAttribute("message", "Update fail");
        return "redirect:/viewTaskList";
    }

    @GetMapping("/AddTask")
    public String addTask(Model model){
        model.addAttribute("todo", new Todo());
        return "AddTask";
    }

    @PostMapping("/saveTask")
    public String saveTask(Todo obj, RedirectAttributes redirectAttributes){
        if(service.saveOrUpdateTask(obj)){
            redirectAttributes.addFlashAttribute("message","Save success");
            return "redirect:/viewTaskList";
        }
        redirectAttributes.addFlashAttribute("message","Save Failure");
        return "redirect:/AddTask";
    }

    @GetMapping("/EditTask/{id}")
    public String editTask(@PathVariable Long id, Model model){
        model.addAttribute("todo", service.getTasksById(id));

        return "EditTask";
    }

    @PostMapping("/editSaveTask")
    public String editSaveTask(Todo obj, RedirectAttributes redirectAttributes){
        if(service.saveOrUpdateTask(obj)){
            redirectAttributes.addFlashAttribute("message","Edit success");
            return "redirect:/viewTaskList";
        }
        redirectAttributes.addFlashAttribute("message","Edit Failure");
        return "redirect:/editTask" + obj.getId();
    }

    @GetMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes){
        if(service.deleteTask(id)){
            redirectAttributes.addFlashAttribute("message","Delete Success");
            return "redirect:/viewTaskList";
        }
        redirectAttributes.addFlashAttribute("message","Delete Failure");
        return "redirect:/viewTaskList";
    }
}
