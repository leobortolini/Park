package br.com.park.park.job;

import br.com.park.park.service.ParkService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ParkSessionJob {

    private final ParkService parkService;

    public ParkSessionJob(ParkService parkService) {
        this.parkService = parkService;
    }

    @Scheduled(fixedRate = 60000)
    public void executeParkSessionJob() {
        parkService.executeParkSessionJob();
    }
}
