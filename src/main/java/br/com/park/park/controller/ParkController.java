package br.com.park.park.controller;

import br.com.park.park.controller.request.ParkRequest;
import br.com.park.park.service.ParkService;
import br.com.park.park.service.ParkService.ParkReceipt;
import br.com.park.park.service.ParkService.ParkState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/park")
@Slf4j
public class ParkController {

    private final ParkService parkService;

    public ParkController(ParkService parkService) {
        this.parkService = parkService;
    }

    @PostMapping
    public ResponseEntity<ParkReceipt> createParkSession(@RequestBody ParkRequest request) {
        ParkReceipt receipt = parkService.park(request.getLicensePlate(), request.getDuration());

        return ResponseEntity.status(HttpStatus.CREATED).body(receipt);
    }

    @GetMapping
    public ResponseEntity<ParkState> getParkSessionState(@RequestParam String licensePlate) {
        ParkState state = parkService.getParkState(licensePlate);

        return ResponseEntity.status(HttpStatus.OK).body(state);
    }

}
