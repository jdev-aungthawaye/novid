package software.techbase.novid.domain.remote.exception;

import java.io.Serializable;

public class RestResponseException extends Exception implements Serializable {

    public String errorMessage;

    RestResponseException(String errorMessage) {

        this.errorMessage = errorMessage;
    }
}
