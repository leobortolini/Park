package br.com.park.park.service.exceptions;

public class ParkingSessionNotFound extends RuntimeException {
    public ParkingSessionNotFound(String message) {
        super(message);
    }
}
