package com.example.todo.service;

import com.example.todo.exception.TaskNotExist;
import com.example.todo.model.TaskDTO;
import com.example.todo.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class TaskServiceTest {

  @Autowired
  TaskRepository repository;
  @Autowired
  ModelMapper modelMapper;
  TaskService service;

  @BeforeEach
  void init() {
    service = new TaskService(repository, modelMapper);
    repository.saveAll(GenerateData.taskList());
  }

  @AfterEach
  void clear() {
    repository.deleteAll();
  }


  @Test()
  void createTask() {
    assertThat(repository.findAll().size()).isEqualTo(6);
    service.createTask("new task");
    assertThat(repository.findAll().size()).isEqualTo(7);
  }

  @Test
  void getAllTasks() {
    assertThat(service.getAllTaskDto().size()).isEqualTo(6);
  }

  @Test
  void deleteTask() {
    var id = service.getAllTaskDto().get(0).getId();
    service.deleteTask(id);
    assertThat(service.getAllTaskDto().size()).isEqualTo(5);
  }

  @Test
  void clearCompletedTasks() {
    service.clearCompletedTasks();
    assertThat(repository.findAllByCompleted(true).size()).isEqualTo(0);
  }

  @Test
  void updateTask() {
    var id = service.getAllTaskDto().get(0).getId();
    service.updateTask(id, TaskDTO.builder().title("update").build());
    assertThat(service.getTask(id).getTitle()).isEqualTo("update");
  }

  @Test
  void getNotExistTask() {
    var id = 1000000L;
    assertThatThrownBy(() -> service.getTask(id)).isInstanceOf(TaskNotExist.class);
  }

  @Test
  void getTaskDto() {
    var dto = service.createTask("todo");
    assertThat(dto.getTitle()).isEqualTo("todo");
    assertThat(dto.getId()).isNotNull();
    assertThat(dto.isCompleted()).isFalse();
  }

}
