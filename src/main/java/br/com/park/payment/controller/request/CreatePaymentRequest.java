package br.com.park.payment.controller.request;

import br.com.park.payment.service.external.PaymentType;
import lombok.Data;

import java.util.UUID;

@Data
public class CreatePaymentRequest {
    private UUID parkSessionId;
    private PaymentType paymentType;
}
