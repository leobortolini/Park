package br.com.park.payment.service;

import br.com.park.park.model.ParkingSession;
import br.com.park.park.service.ParkAdminService;
import br.com.park.payment.model.Payment;
import br.com.park.payment.repository.PaymentRepository;
import br.com.park.payment.service.exceptions.PaymentNotFound;
import br.com.park.payment.service.external.PaymentContext;
import br.com.park.payment.service.external.PaymentType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ParkAdminService parkAdminService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, ParkAdminService parkAdminService) {
        this.paymentRepository = paymentRepository;
        this.parkAdminService = parkAdminService;
    }

    @Override
    public NewPayment createPayment(UUID parkSessionId, PaymentType method) {
        ParkingSession parkingSession = parkAdminService.getUnpaidParkingSession(parkSessionId);
        DurationPrice durationPrice = DurationPrice.fromDuration(parkingSession.getDuration());
        Payment payment = new Payment();

        payment.setParkingSession(parkingSession);
        payment.setPaymentType(method);
        payment.setValue(durationPrice.getPrice());
        payment.setPaymentStatus(PaymentStatus.PENDING);

        payment = paymentRepository.save(payment);

        return new NewPayment(payment.getId(), payment.getPaymentStatus(), payment.getPaymentType(), payment.getValue());
    }

    @Override
    public PaymentReceipt executePayment(UUID id) {
        Optional<Payment> paymentToBeExecuted = paymentRepository.findByIdAndPaymentStatus(id, PaymentStatus.PENDING);

        if (paymentToBeExecuted.isPresent()) {
            Payment payment = paymentToBeExecuted.get();
            PaymentContext paymentContext = getPaymentContext(payment);
            boolean success = paymentContext.executePayment(payment.getValue());
            PaymentStatus newStatus = PaymentStatus.DENIED;

            if (success) {
                newStatus = PaymentStatus.COMPLETED;
                parkAdminService.markAsPaid(payment.getParkingSession().getId());
            }

            payment.setPaymentStatus(newStatus);
            paymentRepository.save(payment);

            return new PaymentReceipt(id, newStatus, payment.getPaymentType());
        }

        throw new PaymentNotFound(String.format("Pending payment with id %s not found", id));
    }

    @Override
    public List<PaymentReceipt> getPayments(UUID parkingSessionId) {
        List<Payment> payments = paymentRepository.findAllByParkingSessionId(parkingSessionId);

        return payments.stream().map(payment -> new PaymentReceipt(payment.getId(), payment.getPaymentStatus(), payment.getPaymentType())).toList();
    }

    private static PaymentContext getPaymentContext(Payment payment) {
        PaymentContext paymentContext = new PaymentContext();

        paymentContext.setPaymentStrategy(payment.getPaymentType());

        return paymentContext;
    }
}
