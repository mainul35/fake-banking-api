package com.mainul35.bank.exceptions.handler;

import com.mainul35.bank.exceptions.NotFoundException;
import com.mainul35.bank.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String SERVICE_EXCEPTION = "SERVICE_EXCEPTION";

    public static final String DUPLICATE = "CONFLICT";

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<?> handleLimitReached(NotFoundException ex) {
        ErrorResponse response = new ErrorResponse(NOT_FOUND, ex.getMessage());
        this.printStackTrace(ex);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ServiceException.class})
    protected ResponseEntity<?> handleLimitReached(ServiceException ex) {
        ErrorResponse response = new ErrorResponse(SERVICE_EXCEPTION, ex.getMessage());
        this.printStackTrace(ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle TypeMismatchException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MethodArgumentNotValidException.class
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ErrorResponse object
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request)  {
        ErrorResponse response = new ErrorResponse(VALIDATION_ERROR, "Request is not valid");
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ItemValidationError> validationErrors = new LinkedList<>();
        fieldErrors.forEach((v) -> {
            validationErrors.add(new ItemValidationError(v.getObjectName(), v.getField(), v.getRejectedValue(), v.getDefaultMessage()));
        });
        response.setErrorItems(validationErrors);

        return this.handleExceptionInternal(ex, response, headers, status, request);
    }

    private void printStackTrace(Exception ex) {
        StackTraceElement[] trace = ex.getStackTrace();
        StringBuilder traceLines = new StringBuilder();
        traceLines.append("Caused By: ").append(ex.fillInStackTrace()).append("\n");
        Arrays.stream(trace).filter(f -> f.getClassName().contains("com.mainul35"))
                .forEach(traceElement -> traceLines.append("\tat ").append(traceElement).append("\n"));
        log.error(traceLines.toString());
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("jakarta.servlet.error.exception", ex, 0);
        }
        if (body == null) {
            body = new ErrorResponse(ex.getClass().getName(), ex.getMessage());
        }
        StackTraceElement[] trace = ex.getStackTrace();
        StringBuilder traceLines = new StringBuilder();
        traceLines.append("Caused By: ").append(ex.fillInStackTrace()).append("\n");
        Arrays.stream(trace).filter(f -> f.getClassName().contains("com.mainul35"))
                .forEach(traceElement -> traceLines.append("\tat ").append(traceElement).append("\n"));
        log.error(traceLines.toString());
        return new ResponseEntity<>(body, headers, status);
    }
}
