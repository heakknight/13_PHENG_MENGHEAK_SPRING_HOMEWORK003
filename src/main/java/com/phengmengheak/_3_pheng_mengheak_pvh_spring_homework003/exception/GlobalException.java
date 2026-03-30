package com.phengmengheak._3_pheng_mengheak_pvh_spring_homework003.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.util.pattern.PathPatternParser;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    private final PathPatternParser pathPatternParser;

    public GlobalException(PathPatternParser pathPatternParser) {
        this.pathPatternParser = pathPatternParser;
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation Failed");

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ProblemDetail handleMethodValidationException(HandlerMethodValidationException e) {
        Map<String, String> errors = new HashMap<>();

        e.getParameterValidationResults().forEach(parameterError -> {
            String paramName = parameterError.getMethodParameter().getParameterName();

            for (var messageError : parameterError.getResolvableErrors()) {
                errors.put(paramName, messageError.getDefaultMessage());
            }
        });

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Method Parameter Validation Failed");
        problemDetail.setProperties(Map.of(
                "timestamp", Instant.now(),
                "errors", errors
        ));

        return problemDetail;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintException(ConstraintViolationException e){
        Map<String, String> err = new HashMap<>();
        e.getConstraintViolations().forEach(v -> {
            String[] path = v.getPropertyPath().toString().split("\\.");
            String field = path[path.length-1];
            err.put(field,v.getMessage());
        });
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Bad Request");
        problemDetail.setProperty("timestamps",Instant.now());
        problemDetail.setProperty("errors",err);
        return problemDetail;
    }
    @ExceptionHandler(UserDuplicateException.class)
    public ProblemDetail handleUserDuplicate(UserDuplicateException e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,e.getMessage());
        problemDetail.setTitle("User conflicted!");
        problemDetail.setType(URI.create("http://localhost:8080/errors/duplicate-user"));
        problemDetail.setProperty("timestamps", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(EmailDuplicateException.class)
    public ProblemDetail handleEmailDuplicate(EmailDuplicateException e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setTitle("Email conflicted!");
        problemDetail.setType(URI.create("http://localhost:8080/errors/duplicate-email"));
        problemDetail.setProperty("timestamps", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(ConflictException.class)
    public ProblemDetail handleConflict(ConflictException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setTitle("Conflicted!");
        problemDetail.setType(URI.create("http://localhost:8080/errors/operation-not-allowed"));
        problemDetail.setProperty("timestamps", Instant.now());
        return problemDetail;
    }
}
