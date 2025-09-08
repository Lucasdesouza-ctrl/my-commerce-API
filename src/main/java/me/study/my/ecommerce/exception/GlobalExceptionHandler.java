package me.study.my.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<ErrorResponse> handleResponseNotFoundException(ResourceNotFoundException error) {

        ErrorResponse errorResponse = new ErrorResponse(
                404,
                error.getMessage(),
                "The requested resource was not found.",
                LocalDateTime.now()
        ) {
        };
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleResponseBadCredentials (BadCredentialsException error) {

        ErrorResponse errorResponse = new ErrorResponse(
                401,
                error.getMessage(),
                "Unauthorized",
                LocalDateTime.now()
        ) {
        };
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

}
