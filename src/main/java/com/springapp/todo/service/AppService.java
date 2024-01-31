package com.springapp.todo.service;

import com.springapp.todo.repo.TodoRepo;
import com.springapp.todo.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppService {

    @Autowired
    TodoRepo repo;

    public List<Todo> getAllTasks(){
        ArrayList<Todo> todoList = new ArrayList<>();
        repo.findAll().forEach(todo -> todoList.add(todo));

        return todoList;
    }

    public Todo getTasksById(Long id){
        return repo.findById(id).get();
    }

    public boolean saveOrUpdateTask(Todo todo){
        Todo updatedObj = repo.save(todo);

        if(getTasksById(updatedObj.getId()) != null){
            return true;
        }

        return false;
    }

    public boolean deleteTask(Long id){
        repo.deleteById(id);

        if(repo.findById(id).isEmpty()){
            return true;
        }

        return false;
    }

    public boolean updateStatus(Long id){
        Todo obj = getTasksById(id);
        obj.setStatus("Completed");

        return saveOrUpdateTask(obj);
    }
}
