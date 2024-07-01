package backend.web.todolist.exception;

import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

public class BadRequestExceptionDetails extends ExceptionsDetails{
    public BadRequestExceptionDetails(String title, int status, String details, String developerMessage, LocalDateTime timestamp){
        super(title, status, details, developerMessage, timestamp);
    }
}
