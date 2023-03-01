package com.example.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TaskNotExist extends NoSuchElementException {
  public TaskNotExist() {
  }

  public TaskNotExist(String message) {
    super(message);
  }

  public TaskNotExist(String message, Throwable cause) {
    super(message, cause);
  }

}
