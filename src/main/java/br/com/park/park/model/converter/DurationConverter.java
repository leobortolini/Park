package br.com.park.park.model.converter;

import jakarta.persistence.AttributeConverter;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class DurationConverter implements AttributeConverter<Duration, String> {

    @Override
    public String convertToDatabaseColumn(Duration attribute) {
        return attribute != null ? attribute.toString() : null;
    }

    @Override
    public Duration convertToEntityAttribute(String dbData) {
        return dbData != null ? Duration.parse(dbData) : null;
    }
}
