package br.com.park.payment.service.external.rails.card;

import br.com.park.payment.service.external.PaymentStrategy;

import java.util.Random;

public class CardPayment implements PaymentStrategy {

    @Override
    public boolean executePayment(double value) {
        System.out.println("Processing card payment.");
        Random random = new Random();

        return random.nextBoolean();
    }
}
