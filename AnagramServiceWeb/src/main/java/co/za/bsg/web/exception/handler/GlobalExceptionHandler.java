package co.za.bsg.web.exception.handler;

import co.za.bsg.domain.exceptions.InternalServerErrorException;
import co.za.bsg.domain.exceptions.NotFoundException;
import co.za.bsg.domain.model.api.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(InternalServerErrorException internalServerErrorException) {
        ErrorResponse errorResponse = new ErrorResponse()
                .setStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .setMessage(internalServerErrorException.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException notFoundException) {
        ErrorResponse errorResponse = new ErrorResponse()
                .setStatusCode(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .setMessage(notFoundException.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
