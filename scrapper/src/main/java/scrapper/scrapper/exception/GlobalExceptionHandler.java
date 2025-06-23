package scrapper.scrapper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import scrapper.scrapper.dto.response.ApiErrorResponse;
import scrapper.scrapper.exception.customExceptions.ChatNotFoundException;
import scrapper.scrapper.exception.customExceptions.DuplicateChatException;
import scrapper.scrapper.exception.customExceptions.LinkAlreadyExistsException;
import scrapper.scrapper.exception.customExceptions.LinkNotFoundException;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(final Exception ex, final WebRequest request) {
        return ResponseEntity.badRequest().body(
                new ApiErrorResponse(
                    request.getDescription(false),
                    HttpStatus.BAD_REQUEST.toString(),
                    ex.getClass().getName(),
                    ex.getMessage(),
                    Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList()
        ));
    }

    @ExceptionHandler({
            DuplicateChatException.class,
            ChatNotFoundException.class,
            LinkAlreadyExistsException.class,
            LinkNotFoundException.class
    })
    public ResponseEntity<ApiErrorResponse> handleCustomException(final RuntimeException ex) {
        HttpStatus status = ex instanceof ChatNotFoundException
            ||
            ex instanceof LinkNotFoundException
                ? HttpStatus.NOT_FOUND
                : HttpStatus.BAD_REQUEST;

        ApiErrorResponse error = new ApiErrorResponse(
                ex.getMessage(),
                String.valueOf(status.value()),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                List.of()
        );
        return new ResponseEntity<>(error, status);
    }
}
