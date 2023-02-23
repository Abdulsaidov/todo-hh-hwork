package com.example.todo.controller;

import com.example.todo.model.Task;
import com.example.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
  private final TaskService taskService;

  @GetMapping("/all")
  public ResponseEntity<List<Task>> getAllTasks() {
    return ResponseEntity.ok(taskService.getAllTasks());
  }

  @PostMapping("/add")
  public ResponseEntity<Task> postTask(@RequestBody String description) {
    return ResponseEntity.ok(taskService.createTask(description));
  }

  @PatchMapping("/update/{id}")
  public ResponseEntity<Task> updateTask(@RequestBody Task task, @PathVariable Long id) {
    return ResponseEntity.ok(taskService.updateTask(id, task));
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Long> deleteTask(@PathVariable Long id) {
    return ResponseEntity.ok(taskService.deleteTask(id));
  }

  @DeleteMapping("/clear")
  public ResponseEntity<HttpStatus> deleteCompletedTask() {
    taskService.clearCompletedTasks();
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
