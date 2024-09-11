package br.com.park.park.model;

import br.com.park.park.model.converter.DurationConverter;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class ParkingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Convert(converter = DurationConverter.class)
    private Duration paidHours;
    private String licensePlate;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
