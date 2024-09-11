package br.com.park.park.service;

import java.time.Duration;
import java.util.UUID;

public interface ParkService {

    ParkReceipt park(String licencePlate, Duration duration);
    ParkState getParkState(String licencePlate);

    record ParkReceipt(UUID identifier) { }
    record ParkState(UUID identifier, Duration remainingTime, ParkStatus status) { }

    enum ParkStatus {
        VALID,
        EXPIRED
    }
}
