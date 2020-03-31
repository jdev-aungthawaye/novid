package jdev.novid.component.asmapper;

import java.util.Map;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;

import jdev.novid.component.asmapper.annotation.AerospikeRecord;

public class AerospikeMapper {

    private ClassSerializer classSerializer;

    private ClassDeserializer classDeserializer;

    public AerospikeMapper() {

        this.classSerializer = new DefaultClassSerializer();
        this.classDeserializer = new DefaultClassDeserializer();
    }

    public <T> T deserialize(Map<String, Object> source, Class<T> clazz) {

        if (!clazz.isAnnotationPresent(AerospikeRecord.class)) {

            throw new UnsupportedClassException(clazz.getName() + " is not annotated with @AerospikeRecord.");
        }

        return this.classDeserializer.deserialize(source, clazz);
    }

    public <T> T deserialize(Record source, Class<T> clazz) {

        return this.deserialize(source.bins, clazz);
    }

    public Bin[] serialize(Object source) {

        Class<?> clazz = source.getClass();

        if (!clazz.isAnnotationPresent(AerospikeRecord.class)) {

            throw new UnsupportedClassException(clazz.getName() + " is not annotated with @AerospikeRecord.");
        }

        return this.classSerializer.serialize(source);
    }
}
