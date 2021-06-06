package com.trendyoltech.linkconverter.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadRequestException
            (BadRequestException ex, WebRequest request, HttpServletRequest httpRequest) {
        ErrorResponse dto = new ErrorResponse();
        dto.setResultCode(HttpStatus.BAD_REQUEST.value());
        dto.setErrorMessage(ex.getMessage());
        dto.setResult(HttpStatus.BAD_REQUEST.name());
        dto.setRequestUrl(httpRequest.getRequestURI());
        return handleExceptionInternal(ex, dto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
