package com.ewmservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;


import java.util.Map;

@RestControllerAdvice
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handle(final ValidationDataException e) {
        return Map.of("error: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handle(final MethodArgumentNotValidException e) {
        return Map.of("error: ", e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage()).findFirst().get());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handle(final UpdateEntityException e) {
        return Map.of("error: ", e.getMessage());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handle(final NotFoundException e) {
        return Map.of("error: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handle(final DuplicateDataException e) {
        return Map.of("error: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handle(final RequestException e) {
        return Map.of("error: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handle(final MissingServletRequestParameterException e) {
        return Map.of("error: ", "Request param don't found");
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handle(final Exception e) {
        return Map.of("error: ", e.getMessage());
    }
}