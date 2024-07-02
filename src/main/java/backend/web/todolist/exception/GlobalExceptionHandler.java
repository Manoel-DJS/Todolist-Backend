package backend.web.todolist.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidUUIDException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleInvalidUUIDException(InvalidUUIDException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException bre){

        BadRequestExceptionDetails details = new BadRequestExceptionDetails(
                "BAD REQUEST",
                HttpStatus.BAD_REQUEST.value(),
                bre.getMessage(),
                bre.getClass().getName(),
                LocalDateTime.now()
        );

        ResponseEntity<BadRequestExceptionDetails> responseEntity = new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    }
