package software.techbase.novid.domain.remote.exception;

public class ResourceNotFoundException extends RestResponseException {

    public ResourceNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
