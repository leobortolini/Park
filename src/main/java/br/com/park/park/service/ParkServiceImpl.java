package br.com.park.park.service;

import br.com.park.park.model.ParkingSession;
import br.com.park.park.repository.ParkingSessionRepository;
import br.com.park.park.service.exceptions.ParkingSessionNotFound;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ParkServiceImpl implements ParkService {

    private final ParkingSessionRepository parkingSessionRepository;

    public ParkServiceImpl(ParkingSessionRepository parkingSessionRepository) {
        this.parkingSessionRepository = parkingSessionRepository;
    }

    @Override
    public ParkReceipt park(String licencePlate, Duration duration) {
        ParkingSession parkingSession = new ParkingSession();

        parkingSession.setPaidHours(duration);
        parkingSession.setLicensePlate(licencePlate);

        parkingSession = parkingSessionRepository.save(parkingSession);

        return new ParkReceipt(parkingSession.getId());
    }

    @Override
    public ParkState getParkState(String licensePlate) {
        Optional<ParkingSession> parkingSession = parkingSessionRepository.findByLicensePlate(licensePlate);

        if (parkingSession.isPresent()) {
            ParkingSession session = parkingSession.get();
            LocalDateTime finalTime = session.getCreatedAt().plus(session.getPaidHours());
            Duration timeLeft = Duration.between(LocalDateTime.now(), finalTime);

            if (timeLeft.isNegative()) {
                return new ParkState(session.getId(), Duration.ZERO, ParkStatus.EXPIRED);
            }

            return new ParkState(session.getId(), timeLeft, ParkStatus.VALID);
        }

        throw new ParkingSessionNotFound(String.format("%s licence plate has no parking session", licensePlate));
    }
}
