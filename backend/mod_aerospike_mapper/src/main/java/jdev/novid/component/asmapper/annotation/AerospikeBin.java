package jdev.novid.component.asmapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jdev.novid.component.asmapper.BinConverter;
import jdev.novid.component.asmapper.SimpleBinConverter;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface AerospikeBin {

    String name();

    Class<? extends BinConverter> converter() default SimpleBinConverter.class;
}
