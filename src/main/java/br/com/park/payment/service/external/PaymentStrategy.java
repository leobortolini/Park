package br.com.park.payment.service.external;

public interface PaymentStrategy {
    boolean executePayment(double value);
}

