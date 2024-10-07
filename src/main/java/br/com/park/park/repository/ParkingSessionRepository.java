package br.com.park.park.repository;

import br.com.park.park.model.ParkingSession;
import br.com.park.park.service.ParkService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParkingSessionRepository extends JpaRepository<ParkingSession, UUID> {
    Optional<ParkingSession> findFirstByLicensePlateOrderByCreatedAtDesc(String licensePlate);
    Optional<ParkingSession> findByIdAndPaid(UUID id, boolean paid);
    List<ParkingSession> findByFinishesAtIsNotNullAndStatus(ParkService.ParkStatus status);
}
