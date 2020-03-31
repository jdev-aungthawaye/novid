package jdev.novid.component.rest;

public class ResourceNotFoundException extends RestException {

    private static final long serialVersionUID = 5974938982040066492L;

    public ResourceNotFoundException() {

        super("RESOURCE_NOT_FOUND_EXCEPTION");
    }

}
