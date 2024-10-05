package br.com.park.config;

import br.com.park.park.service.exceptions.ParkingSessionNotFound;
import br.com.park.authentication.service.exceptions.InvalidAPIKey;
import br.com.park.payment.service.exceptions.InvalidDurationValue;
import br.com.park.payment.service.exceptions.PaymentNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentNotFound.class)
    public ResponseEntity<ErrorResponse> handlePaymentNotFoundException(PaymentNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(InvalidDurationValue.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(InvalidDurationValue ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ParkingSessionNotFound.class)
    public ResponseEntity<ErrorResponse> handleParkingSessionNotFoundException(ParkingSessionNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(InvalidAPIKey.class)
    public ResponseEntity<ErrorResponse> handleInvalidAPIKeyException(InvalidAPIKey ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ErrorResponse> handleIllegalAccessException(IllegalAccessException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(ex.getMessage()));
    }

    public record ErrorResponse(String message) { }
}
