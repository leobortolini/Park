package br.com.park.park.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ParkService {

    ParkReceipt park(String licencePlate, Duration duration);
    ParkState getParkState(String licencePlate);

    record ParkReceipt(UUID identifier, LocalDateTime startedAt, LocalDateTime finishesAt) { }
    record ParkState(UUID identifier, LocalDateTime startedAt, LocalDateTime finishesAt, String remainingTime, ParkStatus status) { }

    enum ParkStatus {
        VALID,
        EXPIRED
    }
}
