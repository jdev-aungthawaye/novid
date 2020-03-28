package jdev.novid.component.asmapper;

import java.util.Map;

public interface ClassDeserializer {
    public <T> T deserialize(Map<String, Object> source, Class<T> clazz);
}
