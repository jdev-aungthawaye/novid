package jdev.novid.common.value;

import java.util.Map;
import java.util.regex.Pattern;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.apache.commons.lang3.Validate;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;

import jdev.novid.common.identity.UserId;
import jdev.novid.component.asmapper.BinConverter;
import jdev.novid.component.ddd.Value;

public class Mobile extends Value {

    @Converter
    public static class JpaConverter implements AttributeConverter<Mobile, String> {

        @Override
        public String convertToDatabaseColumn(Mobile attribute) {

            return attribute == null ? null : attribute.value;

        }

        @Override
        public Mobile convertToEntityAttribute(String dbData) {

            return dbData == null ? null : new Mobile(dbData);

        }

    }

    public static class AerospikeConverter implements BinConverter {

        @Override
        public Object deserialize(String binName, Map<String, Object> source) {

            return new Mobile((String) source.get(binName));

        }

        @Override
        public Object deserialize(String binName, Record source) {

            return new UserId(source.getLong(binName));

        }

        @Override
        public Bin serialize(String binName, Object source) {

            if (source instanceof Mobile) {

                return new Bin(binName, com.aerospike.client.Value.get(((Mobile) source).getValue()));

            }

            throw new IllegalArgumentException("Source is not Mobile.");

        }

    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static final String FORMAT = "^(09|9)\\d{7,}$";

    private static final Pattern PATTERN = Pattern.compile(FORMAT);

    public Mobile(String value) {

        super(value);

        Validate.notNull(value, "Value is required.");

        if (!PATTERN.matcher(value).matches()) {

            throw new IllegalArgumentException("Value is in wrong format.");

        }

    }

}
