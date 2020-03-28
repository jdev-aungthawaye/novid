package jdev.novid.component.asmapper;

import java.lang.reflect.Field;
import java.util.Map;

public interface FieldDeserializer {

    public void deserialize(Object instance, Map<String, Object> source, Field field);
}
