package jdev.novid.component.asmapper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aerospike.client.Value;
import com.github.filosganga.geogson.model.MultiPolygon;
import com.github.filosganga.geogson.model.Point;
import com.github.filosganga.geogson.model.Polygon;

import jdev.novid.component.asmapper.annotation.AerospikeBin;

public class DefaultFieldDeserializer implements FieldDeserializer {

    private static final Logger LOG = LogManager.getLogger(DefaultFieldDeserializer.class);

    @Override
    public void deserialize(Object instance, Map<String, Object> source, Field field) {

        if (!field.isAnnotationPresent(AerospikeBin.class)) {

            LOG.debug("field : {} of class : {} is not annotated with @AerospikeBin...", field.getName(),
                    field.getClass().getName());

            return;
        }

        try {

            field.set(instance, this.toProperty(instance, source, field));

        } catch (IllegalArgumentException | IllegalAccessException e) {
            LOG.error("Error : ", e);
        }
    }

    private Date toDate(Object data, Field field) {

        if (data == null)
            return null;

        if (data instanceof Number == false) {
            throw new FailedToConvertValueException();
        }

        Number num = (Number) data;

        return new Date(num.longValue());
    }

    @SuppressWarnings("unchecked")
    private Object toProperty(Object instance, Map<String, Object> source, Field field)
            throws IllegalArgumentException, IllegalAccessException {

        AerospikeBin annotation = field.getAnnotation(AerospikeBin.class);

        Class<?> fieldType = field.getType();

        if (source.get(annotation.name()) == null)
            return null;

        if (Date.class.isAssignableFrom(fieldType)) {

            return this.toDate(source.get(annotation.name()), field);

        } else if (Calendar.class.isAssignableFrom(fieldType)) {

            Date date = this.toDate(source.get(annotation.name()), field);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            return calendar;

        } else if (Point.class.isAssignableFrom(fieldType)) {

            Object value = source.get(annotation.name());

            if (value instanceof String) {
                return GeoGsonUtil.toPoint((String) value);
            } else
                return GeoGsonUtil.toPoint(((Value.GeoJSONValue) source.get(annotation.name())).toString());

        } else if (Polygon.class.isAssignableFrom(fieldType)) {

            Object value = source.get(annotation.name());

            if (value instanceof String) {
                return GeoGsonUtil.toPolygon((String) value);
            } else
                return GeoGsonUtil.toPolygon(((Value.GeoJSONValue) source.get(annotation.name())).toString());

        } else if (MultiPolygon.class.isAssignableFrom(fieldType)) {

            Object value = source.get(annotation.name());

            if (value instanceof String) {

                return GeoGsonUtil.toMultiPolygon((String) value);

            } else
                return GeoGsonUtil.toMultiPolygon(((Value.GeoJSONValue) source.get(annotation.name())).toString());

        } else if (fieldType.isEnum()) {

            return Enum.valueOf(fieldType.asSubclass(Enum.class), source.get(annotation.name()).toString());

        } else if (Double.class.isAssignableFrom(fieldType)) {

            return Double.valueOf(source.get(annotation.name()).toString());

        } else if (Float.class.isAssignableFrom(fieldType)) {

            return Float.valueOf(source.get(annotation.name()).toString());

        } else if (Long.class.isAssignableFrom(fieldType)) {

            return Long.valueOf(source.get(annotation.name()).toString());

        } else if (Integer.class.isAssignableFrom(fieldType)) {

            return Integer.valueOf(source.get(annotation.name()).toString());

        } else if (BigDecimal.class.isAssignableFrom(fieldType)) {

            return new BigDecimal(source.get(annotation.name()).toString());

        } else if (BigInteger.class.isAssignableFrom(fieldType)) {

            return new BigInteger(source.get(annotation.name()).toString());

        }

        Class<? extends BinConverter> convertClass = annotation.converter();
        BinConverter converter = ConverterContext.getConverter(convertClass);

        Object data = converter.deserialize(annotation.name(), source);

        return data;
    }

}
