package org.example.kardex.exception;

import org.example.kardex.domain.dto.ErrorsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InsufficientStockException.class)
    public final ResponseEntity<ErrorsDto> handleInsufficientStockException(InsufficientStockException ex, WebRequest request) {
        ErrorsDto error = new ErrorsDto("400", ex.getMessage());
        return new ResponseEntity<ErrorsDto>(error, HttpStatus.BAD_REQUEST);
    }
}
