package br.com.park.park.controller;

import br.com.park.park.controller.request.ParkRequest;
import br.com.park.park.service.ParkService;
import br.com.park.park.service.ParkService.ParkReceipt;
import br.com.park.park.service.ParkService.ParkState;
import br.com.park.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/park")
@Slf4j
public class ParkController {

    private final ParkService parkService;
    private final PaymentService paymentService;

    public ParkController(ParkService parkService, PaymentService paymentService) {
        this.parkService = parkService;
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<ParkReceipt> createParkSession(@RequestBody ParkRequest request) {
        if (paymentService.isValidDuration(request.getDuration())) {
            ParkReceipt receipt = parkService.park(request.getLicensePlate(), request.getDuration());

            return ResponseEntity.status(HttpStatus.CREATED).body(receipt);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @GetMapping
    public ResponseEntity<ParkState> getParkSessionState(@RequestHeader("Authorization") String apiKey, @RequestParam String licensePlate) {
        ParkState state = parkService.getParkState(licensePlate);

        return ResponseEntity.status(HttpStatus.OK).body(state);
    }

}
