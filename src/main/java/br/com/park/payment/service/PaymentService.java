package br.com.park.payment.service;

import br.com.park.payment.service.external.PaymentType;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

public interface PaymentService {

    NewPayment createPayment(UUID parkSessionId, PaymentType method);
    PaymentReceipt executePayment(UUID id);
    List<PaymentReceipt> getPayments(UUID parkingSessionId);
    boolean isValidDuration(Duration duration);

    record NewPayment(UUID id, PaymentStatus status, PaymentType type, double value) { }

    record PaymentReceipt(UUID id, PaymentStatus status, PaymentType type) { }

    enum PaymentStatus {
        PENDING,
        COMPLETED,
        DENIED
    }
}
