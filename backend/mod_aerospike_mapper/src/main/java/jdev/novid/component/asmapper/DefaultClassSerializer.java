package jdev.novid.component.asmapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aerospike.client.Bin;

public class DefaultClassSerializer implements ClassSerializer {

    private static final Logger LOG = LogManager.getLogger(DefaultClassSerializer.class);

    private FieldSerializer fieldSerializer;

    public DefaultClassSerializer() {
        this.fieldSerializer = new DefaultFieldSerializer();
    }

    @Override
    public Bin[] serialize(Object source) {

        // We will check which fields of class has @AerospikeBin annotation.
        // If a field got this annotation, we will serialize it to Bin.
        // Otherwise, we ignore.

        Class<?> clazz = source.getClass();
        Field[] fields = clazz.getDeclaredFields();

        List<Bin> bins = new ArrayList<>();

        for (Field field : fields) {

            try {

                field.setAccessible(true);

                Bin bin = this.fieldSerializer.serialize(source, field);

                if (bin != null)
                    bins.add(bin);

            } catch (IllegalArgumentException | IllegalAccessException e) {
                LOG.error("Error : ", e);
            }
        }
        return bins.toArray(new Bin[bins.size()]);
    }

}
