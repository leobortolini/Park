package br.com.park.payment.service.exceptions;

public class PaymentNotFound extends RuntimeException {
    public PaymentNotFound(String message) {
        super(message);
    }
}
