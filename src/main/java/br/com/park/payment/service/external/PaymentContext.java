package br.com.park.payment.service.external;

import br.com.park.payment.service.external.rails.card.CardPayment;
import br.com.park.payment.service.external.rails.cash.CashPayment;

public class PaymentContext {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentType paymentType) {
        switch (paymentType) {
            case CASH:
                this.paymentStrategy = new CashPayment();
                break;
            case CARD:
                this.paymentStrategy = new CardPayment();
                break;
            default:
                throw new IllegalArgumentException("Unknown payment type: " + paymentType);
        }
    }

    public boolean executePayment(double amount) {
        if (paymentStrategy != null) {
            return paymentStrategy.executePayment(amount);
        } else {
            throw new IllegalStateException("Payment strategy not set.");
        }
    }
}