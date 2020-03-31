package software.techbase.novid.domain.remote.exception;

public class UnknownException extends RestResponseException {

    public UnknownException(String errorMessage) {
        super(errorMessage);
    }
}
