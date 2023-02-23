package com.example.todo.service;

import com.example.todo.exception.TaskNotExist;
import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
public class TaskService {
  private final TaskRepository taskRepository;
  private final ExecutorService executorService;

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
    return getAllTasks().stream()
        .filter(Task::isCompleted)
        .toList();
  }

  public Long deleteTask(Long id) {
    taskRepository.deleteById(id);
    return id;
  }

  public void clearCompletedTasks() {
    try {
      deleteCompletedTasksAsync(getAllCompletedTasks()).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  public Task updateTask(Long id, Task task) {
    var current = getTask(id);
    current.setTitle(task.getTitle());
    current.setCreated(task.getCreated());
    current.setCompleted(task.isCompleted());
    current.setUpdated(LocalDateTime.now());
    return taskRepository.save(current);
  }
  //без особого смысла в этой задаче, просто предыдущая тема была про асинк, так что я еще под впечатлением :)
  private CompletableFuture<Void> deleteCompletedTasksAsync(List<Task> list) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return CompletableFuture.allOf(
                list
                    .parallelStream()
                    .map(task -> CompletableFuture.supplyAsync(() -> deleteTask(task.getId()), executorService))
                    .toArray(CompletableFuture[]::new)).get();
          } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
          }
        }
    );

  }
}
