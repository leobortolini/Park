package br.com.park.payment.service.external.rails.cash;

import br.com.park.payment.service.external.PaymentStrategy;

import java.util.Random;

public class CashPayment implements PaymentStrategy {

    @Override
    public boolean executePayment(double value) {
        System.out.println("Processing cash payment.");
        Random random = new Random();

        return random.nextBoolean();
    }
}
