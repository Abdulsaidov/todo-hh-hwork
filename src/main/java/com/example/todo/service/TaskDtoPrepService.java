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


  public TaskDTO getTaskDTO(String title) {
    var task = service.createTask(title);
    return convertToDTO(task);
  }

  public TaskDTO updateTaskDTO( Long id,TaskDTO dto) {
    var task = service.updateTask(id,dto);
    return convertToDTO(task);
  }

  public List<TaskDTO> getAllTaskDTO(){
    return service.getAllTasks().stream().map(this::convertToDTO).collect(Collectors.toList());
  }

  private TaskDTO convertToDTO(Task task) {
    return modelMapper.map(task, TaskDTO.class);
  }

  private Task convertToEntity(TaskDTO dto) {
    return modelMapper.map(dto, Task.class);
  }

}
