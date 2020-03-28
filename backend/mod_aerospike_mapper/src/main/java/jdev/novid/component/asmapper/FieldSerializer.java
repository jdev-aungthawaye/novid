package jdev.novid.component.asmapper;

import java.lang.reflect.Field;

import com.aerospike.client.Bin;

public interface FieldSerializer {

    public Bin serialize(Object instance, Field field) throws IllegalArgumentException, IllegalAccessException;
}
