package br.com.park.park.service;

import br.com.park.park.model.ParkingSession;
import br.com.park.park.repository.ParkingSessionRepository;
import br.com.park.park.service.exceptions.ParkingSessionNotFound;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static br.com.park.park.service.util.DurationHumanReadableUtil.formatDuration;

@Service
public class ParkServiceImpl implements ParkService {

    private final ParkingSessionRepository parkingSessionRepository;

    public ParkServiceImpl(ParkingSessionRepository parkingSessionRepository) {
        this.parkingSessionRepository = parkingSessionRepository;
    }

    @Override
    public ParkReceipt park(String licencePlate, Duration duration) {
        ParkingSession parkingSession = new ParkingSession();

        parkingSession.setDuration(duration);
        parkingSession.setLicensePlate(licencePlate);
        parkingSession.setPaid(false);

        parkingSession = parkingSessionRepository.save(parkingSession);

        return new ParkReceipt(parkingSession.getId());
    }

    @Override
    public ParkState getParkState(String licensePlate) {
        Optional<ParkingSession> parkingSession = parkingSessionRepository.findByLicensePlate(licensePlate);

        if (parkingSession.isPresent()) {
            ParkingSession session = parkingSession.get();
            boolean paid = session.isPaid();

            if (!paid) {
                return new ParkState(session.getId(), null, null, null, ParkStatus.PENDING, false);
            }

            LocalDateTime finalTime = session.getFinishesAt();
            Duration timeLeft = Duration.between(LocalDateTime.now(), finalTime);
            ParkStatus status = timeLeft.isNegative() ? ParkStatus.EXPIRED : ParkStatus.VALID;

            return new ParkState(session.getId(), session.getCreatedAt(), finalTime, formatDuration(timeLeft), status, true);
        }

        throw new ParkingSessionNotFound(String.format("%s licence plate has no parking session", licensePlate));
    }
}
