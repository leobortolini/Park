package br.com.park.park.repository;

import br.com.park.park.model.ParkingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ParkingSessionRepository extends JpaRepository<ParkingSession, UUID> {
    Optional<ParkingSession> findFirstByLicensePlate(String licensePlate);
    Optional<ParkingSession> findByIdAndPaid(UUID id, boolean paid);
}
