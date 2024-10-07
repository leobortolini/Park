package br.com.park.park.service;

import br.com.park.park.model.ParkingSession;
import br.com.park.park.repository.ParkingSessionRepository;
import br.com.park.park.service.exceptions.ParkingSessionNotFound;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ParkServiceImpl implements ParkService {

    private final ParkingSessionRepository parkingSessionRepository;
    private final CacheManager cacheManager;

    public ParkServiceImpl(ParkingSessionRepository parkingSessionRepository, CacheManager cacheManager) {
        this.parkingSessionRepository = parkingSessionRepository;
        this.cacheManager = cacheManager;
    }

    @Override
    public ParkReceipt park(String licencePlate, Duration duration) {
        ParkingSession parkingSession = new ParkingSession();

        parkingSession.setDuration(duration);
        parkingSession.setLicensePlate(licencePlate);
        parkingSession.setPaid(false);
        parkingSession.setStatus(ParkStatus.PENDING);

        parkingSession = parkingSessionRepository.save(parkingSession);

        return new ParkReceipt(parkingSession.getId());
    }

    @Cacheable(value = "parkState", key = "#licensePlate")
    @Override
    public ParkState getParkState(String licensePlate) {
        Optional<ParkingSession> parkingSession = parkingSessionRepository.findFirstByLicensePlateOrderByCreatedAtDesc(licensePlate);

        if (parkingSession.isPresent()) {
            ParkingSession session = parkingSession.get();

            return new ParkState(session.getId(), session.getStartsAt(), session.getFinishesAt(), parkingSession.get().getStatus(), session.isPaid());
        }

        throw new ParkingSessionNotFound(String.format("%s licence plate has no parking session", licensePlate));
    }

    @Override
    public void executeParkSessionJob() {
        parkingSessionRepository.findByFinishesAtIsNotNull().forEach(parkingSession -> {
            if (parkingSession.getFinishesAt() != null && parkingSession.getFinishesAt().isBefore(LocalDateTime.now())) {
                parkingSession.setStatus(ParkStatus.EXPIRED);
                parkingSessionRepository.save(parkingSession);
                Cache parkStateCache = cacheManager.getCache("parkState");

                if (parkStateCache != null) {
                    parkStateCache.evict(parkingSession.getLicensePlate());
                }
            }
        });
    }
}
