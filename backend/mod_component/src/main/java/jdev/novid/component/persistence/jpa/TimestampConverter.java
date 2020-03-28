package jdev.novid.component.persistence.jpa;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TimestampConverter implements AttributeConverter<LocalDateTime, Long> {

    @Override
    public Long convertToDatabaseColumn(LocalDateTime attribute) {

        if (attribute == null)
            return null;

        return attribute.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

    }

    @Override
    public LocalDateTime convertToEntityAttribute(Long dbData) {

        if (dbData == null)
            return null;

        LocalDateTime timestamp = Instant.ofEpochMilli(dbData).atZone(ZoneId.systemDefault()).toLocalDateTime();

        return timestamp;

    }

}