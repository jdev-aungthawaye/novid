package jdev.novid.component.rest;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestErrorResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String errorCode;
}