package jdev.novid.component.rest;

public class RestException extends Exception {

    private static final long serialVersionUID = 7230685161631046748L;

    public String errorCode;

    public RestException(String errorCode) {

        this.errorCode = errorCode;
    }

    @Override
    public String toString() {

        return this.errorCode;
    }
}
