package jdev.novid.component.util;

public class EnvAwareUnitTest {

    static {

        System.setProperty("NOVID_JASYPT_PASSWORD", "password");

    }

}