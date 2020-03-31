package jdev.novid.component.asmapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultClassDeserializer implements ClassDeserializer {

    private static final Logger LOG = LogManager.getLogger(DefaultClassDeserializer.class);

    private FieldDeserializer fieldDeserializer;

    public DefaultClassDeserializer() {

        this.fieldDeserializer = new DefaultFieldDeserializer();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(Map<String, Object> source, Class<T> clazz) {

        try {

            Object instance = this.newInstance(clazz);

            Field fields[] = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                this.fieldDeserializer.deserialize(instance, source, field);
            }

            return (T) instance;
        } catch (
                InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException e) {
            LOG.error("Error : ", e);
            throw new UnableToInstantiateException("Make sure class [" + clazz.getName()
                    + "] has default constructor and accessible. And class must not be abstract.");
        }
    }

    private <T> Object newInstance(Class<T> clazz)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        for (Constructor<?> constructor : constructors) {

            if (Modifier.isProtected(constructor.getModifiers()) || Modifier.isPrivate(constructor.getModifiers())) {

                constructor.setAccessible(true);
            }

            if (constructor.getParameterCount() == 0) {

                return constructor.newInstance(new Object[0]);
            }
        }

        throw new IllegalArgumentException("Require no arguments constructor.");
    }

}
