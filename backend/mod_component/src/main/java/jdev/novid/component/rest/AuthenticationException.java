package jdev.novid.component.rest;

public class AuthenticationException extends RestException {

    private static final long serialVersionUID = 4062679624836917363L;

    public AuthenticationException() {

        super("AUTHENTICATION_EXCEPTION");
    }

}