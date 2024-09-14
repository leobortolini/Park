package br.com.park.payment.model;

import br.com.park.park.model.ParkingSession;
import br.com.park.payment.service.PaymentService.PaymentStatus;
import br.com.park.payment.service.external.PaymentType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private double value;
    @ManyToOne
    private ParkingSession parkingSession;
}
