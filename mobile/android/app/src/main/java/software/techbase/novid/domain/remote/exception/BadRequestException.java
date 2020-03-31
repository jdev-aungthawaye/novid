package software.techbase.novid.domain.remote.exception;

/**
 * Created by Wai Yan on 12/4/18.
 */
public class BadRequestException extends RestResponseException {

    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
