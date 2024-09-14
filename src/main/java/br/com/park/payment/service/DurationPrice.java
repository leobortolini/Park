package br.com.park.payment.service;

import br.com.park.payment.service.exceptions.InvalidDurationValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

@Getter
@AllArgsConstructor
public enum DurationPrice {

    THIRTY_MINUTES(Duration.ofMinutes(30), 1.50),
    ONE_HOUR(Duration.ofHours(1), 3.00),
    ONE_AND_HALF_HOUR(Duration.ofMinutes(90), 4.50),
    TWO_HOURS(Duration.ofHours(2), 6.00);

    private final Duration duration;
    private final double price;

    public static DurationPrice fromDuration(Duration duration) {
        for (DurationPrice dp : DurationPrice.values()) {
            if (dp.getDuration().equals(duration)) {
                return dp;
            }
        }
        throw new InvalidDurationValue("Duration " + duration + " has no value associated");
    }
}
