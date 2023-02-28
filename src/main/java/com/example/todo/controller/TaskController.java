package com.example.todo.controller;

import com.example.todo.model.TaskDTO;
import com.example.todo.service.TaskDtoPrepService;
import com.example.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
  private final TaskService taskService;
  private final TaskDtoPrepService prepService;

  @GetMapping("/all")
  public List<TaskDTO> getAllTasks() {
    return prepService.getAllTaskDTO();
  }

  @PostMapping("/add")
  public TaskDTO postTask(@RequestBody String description) {
    return prepService.getTaskDTO(description);
  }

  @PatchMapping("/update/{id}")
  public TaskDTO updateTask(@RequestBody TaskDTO task, @PathVariable Long id) {
    return prepService.updateTaskDTO(id, task);
  }

  @DeleteMapping("/delete/{id}")
  public Long deleteTask(@PathVariable Long id) {
    return taskService.deleteTask(id);
  }

  @DeleteMapping("/clear")
  public void deleteCompletedTask() {
    taskService.clearCompletedTasks();
  }

}
