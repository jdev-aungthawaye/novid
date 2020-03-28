package jdev.novid.component.asmapper;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.xml.txw2.IllegalSignatureException;

public class ConverterContext {

    private static final Logger LOG = LogManager.getLogger(ConverterContext.class);

    public static Map<Class<? extends BinConverter>, BinConverter> converters = new HashMap<Class<? extends BinConverter>, BinConverter>();

    public static BinConverter getConverter(Class<? extends BinConverter> clazz) {

        LOG.debug("finding converter for class : {}", clazz.getName());

        BinConverter converter = converters.get(clazz);

        if (converter == null) {

            try {
                converter = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalSignatureException("Could not find converter.");
            }
            converters.put(clazz, converter);
        }

        return converter;

    }
}
