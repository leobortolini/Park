package br.com.park.park.controller.request;

import lombok.Data;

import java.time.Duration;

@Data
public class ParkRequest {
    private String licensePlate;
    private Duration duration;
}
