package com.ryzendee.moduleservice.controller;

import com.ryzendee.moduleservice.dto.exception.ExceptionResponse;
import com.ryzendee.moduleservice.dto.exception.ValidationFieldErrorResponse;
import com.ryzendee.moduleservice.exception.LearningModuleNotFoundException;
import com.ryzendee.moduleservice.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ExceptionResponse handleLearningModuleNotFoundEx(ResourceNotFoundException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public List<ValidationFieldErrorResponse> handleMethodArgumentNotValidEx(MethodArgumentNotValidException ex) {
        return ex.getFieldErrors().stream()
                .map(error -> new ValidationFieldErrorResponse(error.getField(), error.getDefaultMessage()))
                .toList();
    }
}
