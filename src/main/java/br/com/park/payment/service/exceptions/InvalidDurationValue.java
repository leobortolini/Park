package br.com.park.payment.service.exceptions;

public class InvalidDurationValue extends RuntimeException {
    public InvalidDurationValue(String message) {
        super(message);
    }
}
