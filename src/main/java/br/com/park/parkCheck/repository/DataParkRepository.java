package br.com.park.parkCheck.repository;

import br.com.park.park.model.ParkingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DataParkRepository extends JpaRepository<ParkingSession, UUID>{
        Optional<ParkingSession> findFirstByLicensePlate(String licensePlate);
}
