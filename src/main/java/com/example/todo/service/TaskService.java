package com.example.todo.service;

import com.example.todo.exception.TaskNotExist;
import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
  private final TaskRepository taskRepository;

  public Task createTask(String title) {
    var dateTime = LocalDateTime.now();
    return taskRepository.save(
        Task.builder()
            .title(title)
            .created(dateTime)
            .updated(dateTime)
            .completed(false)
            .build());
  }

  public Task getTask(Long id) {
    return taskRepository.findById(id).orElseThrow(() -> new TaskNotExist("task # " + id + " is not exist yet"));
  }

  public List<Task> getAllTasks() {
    return taskRepository.findAll();
  }

  public List<Task> getAllCompletedTasks() {
    return taskRepository.findAllByCompleted(true);
  }

  @Transactional
  public Long deleteTask(Long id) {
    taskRepository.deleteById(id);
    return id;
  }

  @Transactional
  public void clearCompletedTasks() {
    var completedList = getAllCompletedTasks();
    taskRepository.flush();
    taskRepository.deleteAllInBatch(completedList);
  }

  public Task updateTask(Long id, Task task) {
    var current = getTask(id);
    current.setTitle(task.getTitle());
    current.setCreated(task.getCreated());
    current.setCompleted(task.isCompleted());
    current.setUpdated(LocalDateTime.now());
    return taskRepository.save(current);
  }

}
