package com.example.todo.service;

import com.example.todo.model.Task;
import com.example.todo.model.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TaskDtoPrepService {
  private final TaskService service;
  private final ModelMapper modelMapper;



  public TaskDTO getTaskDto(String title) {
    var task = service.createTask(title);
    return convertToDto(task);
  }

  public TaskDTO updateTaskDto( Long id,TaskDTO dto) {
    var task = service.updateTask(id,dto);
    return convertToDto(task);
  }

  public List<TaskDTO> getAllTaskDto(){
    return service.getAllTasks().stream().map(this::convertToDto).collect(Collectors.toList());
  }

  private TaskDTO convertToDto(Task task) {
    return modelMapper.map(task, TaskDTO.class);
  }

  private Task convertToEntity(TaskDTO dto) {
    return modelMapper.map(dto, Task.class);
  }

}
