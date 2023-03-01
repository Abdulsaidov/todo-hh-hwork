package com.example.todo.service;

import com.example.todo.exception.TaskNotExist;
import com.example.todo.model.Task;
import com.example.todo.model.TaskDto;
import com.example.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
  private final TaskRepository taskRepository;
  private final ModelMapper modelMapper;

  public TaskDto createTask(String title) {
    var dateTime = LocalDateTime.now();
    return convertToDto(taskRepository.save(
        Task.builder()
            .title(title)
            .created(dateTime)
            .updated(dateTime)
            .completed(false)
            .build()));
  }

  @Transactional
  public Long deleteTask(Long id) {
    taskRepository.deleteById(id);
    return id;
  }

  @Transactional
  public TaskDto updateTask(Long id, TaskDto task) {
    var current = getTask(id);
    current.setTitle(task.getTitle());
    current.setCompleted(task.isCompleted());
    current.setUpdated(LocalDateTime.now());
    return convertToDto(taskRepository.save(current));
  }

  public List<TaskDto> getAllTaskDto(){
    return taskRepository.findAll().stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
  }

  @Transactional
  public void clearCompletedTasks() {
    taskRepository.flush();
    var completedList = taskRepository.findAllByCompleted(true);
    taskRepository.deleteAllInBatch(completedList);
  }

  public Task getTask(Long id) {
    return taskRepository.findById(id).orElseThrow(() -> new TaskNotExist("task # " + id + " is not exist yet"));
  }
  private TaskDto convertToDto(Task task) {
    return modelMapper.map(task, TaskDto.class);
  }

}
