package jdev.novid.component.rest;

import lombok.Getter;

@Getter
public class BadRequestException extends RestException {

    private static final long serialVersionUID = 2895146036460151014L;

    private String body;

    public BadRequestException(String errorCode, String body) {

        super(errorCode);
        this.body = body;
    }

}
