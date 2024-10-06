package br.com.park.config;

import br.com.park.park.service.exceptions.ParkingSessionNotFound;
import br.com.park.payment.service.exceptions.InvalidDurationValue;
import br.com.park.payment.service.exceptions.PaymentNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentNotFound.class)
    public ResponseEntity<String> handlePaymentNotFoundException(PaymentNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDurationValue.class)
    public ResponseEntity<String> handleNotFoundException(InvalidDurationValue ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParkingSessionNotFound.class)
    public ResponseEntity<String> handleParkingSessionNotFoundException(ParkingSessionNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
