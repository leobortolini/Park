package br.com.park.parkCheck.controller;

import br.com.park.park.model.ParkingSession;
import br.com.park.parkCheck.service.DataParkService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/parkCheck/dataPark")
public class DataParkController {

    private final DataParkService dataParkService;

    public DataParkController(DataParkService dataParkService) {
        this.dataParkService = dataParkService;
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity searchCar(@PathVariable String licensePlate, @RequestHeader("Authorization") String apiKey) {
        try{
            if(apiKey == null || apiKey.isEmpty()){
                throw new IllegalAccessException("API Key inválida.");
            }

            if(licensePlate == null || licensePlate.isEmpty()){
                throw new IllegalArgumentException("LicensePlate inválida.");
            }

            ParkingSession parkingSession = dataParkService.getDataPark(apiKey, licensePlate);

            return ResponseEntity.ok(parkingSession);

        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar placa do carro.");
        }
    }
}
