package br.com.park.park.service;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ParkService {

    ParkReceipt park(String licencePlate, Duration duration);
    ParkState getParkState(String licencePlate);
    void executeParkSessionJob();

    record ParkReceipt(UUID identifier) { }
    record ParkState(UUID identifier, LocalDateTime startedAt, LocalDateTime finishesAt, ParkStatus status, boolean paid) implements Serializable { }

    enum ParkStatus {
        VALID,
        PENDING,
        EXPIRED
    }
}
