package com.example.todo.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
  private Long id;
  private String title;
  private boolean completed;
}
