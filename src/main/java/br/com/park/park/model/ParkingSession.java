package br.com.park.park.model;

import br.com.park.park.model.converter.DurationConverter;
import br.com.park.payment.model.Payment;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class ParkingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Convert(converter = DurationConverter.class)
    private Duration duration;
    private String licensePlate;
    private boolean paid;
    @OneToMany(mappedBy = "parkingSession", fetch = FetchType.EAGER)
    private List<Payment> payments = new ArrayList<>();
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime startsAt;
    private LocalDateTime finishesAt;
}
