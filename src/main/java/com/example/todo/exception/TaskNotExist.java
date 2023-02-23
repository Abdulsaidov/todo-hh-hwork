package com.example.todo.exception;

public class TaskNotExist extends RuntimeException {
  public TaskNotExist() {
  }

  public TaskNotExist(String message) {
    super(message);
  }

  public TaskNotExist(String message, Throwable cause) {
    super(message, cause);
  }
}
