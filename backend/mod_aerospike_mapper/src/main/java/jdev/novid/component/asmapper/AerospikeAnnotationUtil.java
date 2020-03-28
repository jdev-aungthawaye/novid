package jdev.novid.component.asmapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdev.novid.component.asmapper.annotation.AerospikeBin;
import jdev.novid.component.asmapper.annotation.AerospikeRecord;

public class AerospikeAnnotationUtil {

    private final static Logger LOG = LogManager.getLogger(AerospikeAnnotationUtil.class);

    public static boolean isAerospikeBinField(Field field) {

        Annotation[] annotations = field.getAnnotations();

        for (Annotation annotation : annotations) {

            LOG.debug("found annotation : {} on field : {}", annotation.getClass().getName(), field.getName());

            if (annotation instanceof AerospikeBin) {

                LOG.debug("field : {} has @AerospikeBin annotation.", field.getName());
                return true;

            }

        }

        return false;

    }

    public static boolean isAerospikeRecordClass(Class<?> clazz) {

        Annotation[] annotations = clazz.getAnnotations();

        for (Annotation annotation : annotations) {

            LOG.debug("found annotation : {} on class : {}", annotation.getClass().getName(), clazz.getName());

            if (annotation instanceof AerospikeRecord) {

                LOG.debug("class : {} has @AerospikeRecord annotation.", annotation.getClass().getName(),
                        clazz.getName());
                return true;

            }

        }

        return false;

    }

}
