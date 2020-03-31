package software.techbase.novid.domain.remote.exception;

/**
 * Created by Wai Yan on 12/4/18.
 */
public class AuthenticationException extends RestResponseException {

    public AuthenticationException(String errorMessage) {
        super(errorMessage);
    }
}
