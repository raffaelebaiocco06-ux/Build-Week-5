package Build_week.build_week.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNotFound(NotFoundException e) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("message", e.getMessage());
        payload.put("timestamp", LocalDateTime.now());
        payload.put("status", 404);
        return payload;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleGeneric(Exception e) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("message", "Errore generico lato server");
        payload.put("timestamp", LocalDateTime.now());
        payload.put("status", 500);
        return payload;
    }
}