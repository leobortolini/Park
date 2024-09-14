package br.com.park.payment.controller;

import br.com.park.payment.controller.request.CreatePaymentRequest;
import br.com.park.payment.service.PaymentService;
import br.com.park.payment.service.PaymentService.NewPayment;
import br.com.park.payment.service.PaymentService.PaymentReceipt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("api/v1/payment")
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<NewPayment> createPayment(@RequestBody CreatePaymentRequest request) {
        NewPayment payment = paymentService.createPayment(request.getParkSessionId(), request.getPaymentType());

        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    @PostMapping("/pay")
    public ResponseEntity<PaymentReceipt> executePayment(@RequestParam UUID id) {
        PaymentReceipt receipt = paymentService.executePayment(id);

        return ResponseEntity.status(HttpStatus.OK).body(receipt);
    }

    @GetMapping("/payments")
    public ResponseEntity<List<PaymentReceipt>> getPayments(@RequestParam UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPayments(id));
    }

}