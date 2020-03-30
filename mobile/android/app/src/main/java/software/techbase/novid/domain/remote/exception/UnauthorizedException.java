package software.techbase.novid.domain.remote.exception;

public class UnauthorizedException extends RestResponseException {

    public UnauthorizedException(String errorMessage) {
        super(errorMessage);
    }
}
