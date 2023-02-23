package com.example.todo.service;

import com.example.todo.model.Task;

import java.util.ArrayList;
import java.util.List;

public class GenerateData {
  public static List<Task> taskList() {
    List<Task> list = new ArrayList<>();
    for (long i = 1; i < 6; i++) {
      if (i % 2 == 0) {
        list.add(Task.builder()
            .id(i)
            .title("задача № " + i)
            .completed(false)
            .build());
      } else {
        list.add(Task.builder()
            .id(i)
            .title("задача № " + i)
            .completed(true)
            .build());
      }
    }
    list.add(Task.builder()
        .id(10L)
        .title("задача № 10")
        .completed(false)
        .build());
    return list;
  }
}
