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

public class Nric extends Value {

    @Converter
    public static class JpaConverter implements AttributeConverter<Nric, String> {

        @Override
        public String convertToDatabaseColumn(Nric attribute) {

            return attribute == null ? null : attribute.value;

        }

        @Override
        public Nric convertToEntityAttribute(String dbData) {

            return dbData == null ? null : new Nric(dbData);

        }

    }

    public static class AerospikeConverter implements BinConverter {

        @Override
        public Object deserialize(String binName, Map<String, Object> source) {

            return new Nric((String) source.get(binName));

        }

        @Override
        public Object deserialize(String binName, Record source) {

            return new UserId(source.getLong(binName));

        }

        @Override
        public Bin serialize(String binName, Object source) {

            if (source instanceof Nric) {

                return new Bin(binName, com.aerospike.client.Value.get(((Nric) source).getValue()));

            }

            throw new IllegalArgumentException("Source is not Nric.");

        }

    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Nric(String value) {

        super(value);

        Validate.notNull(value, "Value is required.");

    }

}
