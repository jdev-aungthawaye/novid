package jdev.novid.component.persistence.jpa;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;

import jdev.novid.component.asmapper.BinConverter;

@Converter
public class TimestampConverter implements AttributeConverter<LocalDateTime, Long>, BinConverter {

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

    @Override
    public Object deserialize(String binName, Map<String, Object> source) {

        LocalDateTime timestamp = Instant.ofEpochMilli((Long) source.get(binName)).atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return timestamp;

    }

    @Override
    public Object deserialize(String binName, Record source) {

        LocalDateTime timestamp = Instant.ofEpochMilli(source.getLong(binName)).atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return timestamp;

    }

    @Override
    public Bin serialize(String binName, Object source) {

        if (source instanceof LocalDateTime) {

            long timestamp = ((LocalDateTime) source).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

            return new Bin(binName, com.aerospike.client.Value.get(timestamp));

        }

        throw new IllegalArgumentException("Source is not LocalDateTime.");

    }

}