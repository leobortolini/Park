package br.com.park.park.service;

import br.com.park.park.model.ParkingSession;

import java.util.UUID;

public interface ParkAdminService {
    ParkingSession getUnpaidParkingSession(UUID id);
    void markAsPaid(UUID id);
}
