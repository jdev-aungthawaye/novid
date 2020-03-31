package software.techbase.novid.domain.remote.exception;

/**
 * Created by Wai Yan on 2019-08-04.
 */
public class UnProcessableException extends RestResponseException {

    public UnProcessableException(String errorMessage) {
        super(errorMessage);
    }
}
