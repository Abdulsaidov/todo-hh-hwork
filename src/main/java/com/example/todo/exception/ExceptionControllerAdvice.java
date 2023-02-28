package com.example.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TaskNotExist.class)
    public final ResponseEntity<Object> handleTaskNotExist(TaskNotExist ex) {
        System.out.println(ex.getLocalizedMessage());
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Такой todo еще не создано", details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
