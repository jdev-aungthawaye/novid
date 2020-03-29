package jdev.novid.common.value;

import java.util.regex.Pattern;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.apache.commons.lang3.Validate;

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
