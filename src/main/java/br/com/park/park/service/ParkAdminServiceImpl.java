package br.com.park.park.service;

import br.com.park.park.model.ParkingSession;
import br.com.park.park.repository.ParkingSessionRepository;
import br.com.park.park.service.exceptions.ParkingSessionNotFound;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkAdminServiceImpl implements ParkAdminService {

    private final ParkingSessionRepository parkingSessionRepository;

    public ParkAdminServiceImpl(ParkingSessionRepository parkingSessionRepository) {
        this.parkingSessionRepository = parkingSessionRepository;
    }

    @Override
    public ParkingSession getUnpaidParkingSession(UUID id) {
        Optional<ParkingSession> parkingSession = parkingSessionRepository.findByIdAndPaid(id, false);

        if (parkingSession.isPresent())
            return parkingSession.get();

        throw new ParkingSessionNotFound(String.format("unpaid parking session with id %s not found", id));
    }

    @Override
    public void markAsPaid(UUID id) {
        Optional<ParkingSession> parkingSession = parkingSessionRepository.findById(id);

        if (parkingSession.isPresent()) {
            ParkingSession session = parkingSession.get();

            session.setPaid(true);
            LocalDateTime now = LocalDateTime.now();
            session.setStartsAt(now);
            session.setFinishesAt(now.plus(session.getDuration()));

            parkingSessionRepository.save(session);
            return;
        }

        throw new ParkingSessionNotFound(String.format("parking session with id %s not found", id));
    }
}
