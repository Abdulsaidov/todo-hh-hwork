package com.example.todo.controller;

import com.example.todo.model.TaskDTO;
import com.example.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
  private final TaskService taskService;

  @GetMapping("/all")
  public List<TaskDTO> getAllTasks() {
    return taskService.getAllTaskDto();
  }

  @PostMapping("/add")
  public TaskDTO postTask(@RequestBody String description) {
    return taskService.createTask(description);
  }

  @PatchMapping("/update/{id}")
  public TaskDTO updateTask(@RequestBody TaskDTO task, @PathVariable Long id) {
    return taskService.updateTask(id, task);
  }

  @DeleteMapping("/delete/{id}")
  public Long deleteTask(@PathVariable Long id) {
    return taskService.deleteTask(id);
  }

  @DeleteMapping("/clear")
  @ResponseStatus(HttpStatus.OK)
  public void deleteCompletedTask() {
    taskService.clearCompletedTasks();
  }

}
