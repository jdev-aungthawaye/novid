package jdev.novid.component.asmapper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aerospike.client.Bin;
import com.aerospike.client.Value;
import com.github.filosganga.geogson.model.MultiPolygon;
import com.github.filosganga.geogson.model.Point;
import com.github.filosganga.geogson.model.Polygon;

import jdev.novid.component.asmapper.annotation.AerospikeBin;

public class DefaultFieldSerializer implements FieldSerializer {

    private static final Logger LOG = LogManager.getLogger(DefaultFieldSerializer.class);

    public DefaultFieldSerializer() {

    }

    @Override
    public Bin serialize(Object instance, Field field) throws IllegalArgumentException, IllegalAccessException {

        if (!field.isAnnotationPresent(AerospikeBin.class)) {
            LOG.debug("field : {} of class : {} is not annotated with @AerospikeBin...", field.getName(),
                    field.getClass().getName());
            return null;
        }

        LOG.debug("serializing field : {} of class : {}", field.getName(), field.getClass().getName());

        return this.toBin(instance, field);
    }

    private Bin toBin(Object instance, Field field) throws IllegalArgumentException, IllegalAccessException {

        AerospikeBin annotation = field.getAnnotation(AerospikeBin.class);

        if (field.get(instance) == null) {
            LOG.debug("field : {} of class : {} is NULL", field.getName(), field.getClass().getName());
            return Bin.asNull(annotation.name());
        }

        Class<?> fieldType = field.getType();

        if (Date.class.isAssignableFrom(fieldType)) {

            return new Bin(annotation.name(), ((Date) field.get(instance)).getTime());

        } else if (Calendar.class.isAssignableFrom(fieldType)) {

            return new Bin(annotation.name(), ((Calendar) field.get(instance)).getTimeInMillis());

        } else if (Point.class.isAssignableFrom(fieldType)) {

            return new Bin(annotation.name(), Value.getAsGeoJSON(GeoGsonUtil.fromPoint((Point) field.get(instance))));

        } else if (Polygon.class.isAssignableFrom(fieldType)) {

            return new Bin(annotation.name(),
                    Value.getAsGeoJSON(GeoGsonUtil.fromPolygon((Polygon) field.get(instance))));

        } else if (MultiPolygon.class.isAssignableFrom(fieldType)) {

            return new Bin(annotation.name(),
                    Value.getAsGeoJSON(GeoGsonUtil.fromMultiPolygon((MultiPolygon) field.get(instance))));

        } else if (fieldType.isEnum()) {

            return new Bin(annotation.name(), (field.get(instance)).toString());

        } else if (BigDecimal.class.isAssignableFrom(fieldType)) {

            return new Bin(annotation.name(), ((BigDecimal) field.get(instance)).toPlainString());

        } else if (BigInteger.class.isAssignableFrom(fieldType)) {

            return new Bin(annotation.name(), ((BigInteger) field.get(instance)).toString());

        } else if (ClassUtils.isPrimitiveOrWrapper(fieldType)) {

            return new Bin(annotation.name(), field.get(instance));
        }

        Class<? extends BinConverter> convertClass = annotation.converter();
        BinConverter converter = ConverterContext.getConverter(convertClass);

        Bin bin = converter.serialize(annotation.name(), field.get(instance));

        return bin;
    }

}
