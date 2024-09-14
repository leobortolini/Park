package br.com.park.payment.repository;

import br.com.park.payment.model.Payment;
import br.com.park.payment.service.PaymentService.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByIdAndPaymentStatus(UUID id, PaymentStatus status);
    List<Payment> findAllByParkingSessionId(UUID parkingSessionId);
}
