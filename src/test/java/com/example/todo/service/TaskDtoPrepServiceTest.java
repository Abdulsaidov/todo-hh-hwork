package com.example.todo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SpringBootTest
class TaskDtoPrepServiceTest {
  TaskDtoPrepService prepService;

  @Autowired
  ModelMapper modelMapper;
  @Autowired
  TaskService service;

  @BeforeEach
  void init(){
    prepService = new TaskDtoPrepService(service,modelMapper);
  }

  @Test
  void getTaskDTO()  {
    var dto = prepService.getTaskDTO("todo");
    assertThat(dto.getTitle()).isEqualTo("todo");
    assertThat(dto.getId()).isNotNull();
    assertThat(dto.isCompleted()).isFalse();
  }
}
