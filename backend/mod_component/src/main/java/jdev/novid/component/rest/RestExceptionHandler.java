package jdev.novid.component.rest;

import java.io.Serializable;

import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jdev.novid.component.ddd.DomainException;
import lombok.Getter;

@RestControllerAdvice
public class RestExceptionHandler {

    @Getter
    public static class ErrorResponse implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        private String errorCode;

        public ErrorResponse(String errorCode) {

            super();

            this.errorCode = errorCode;

        }

    }

    private final static Logger LOG = LogManager.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse processEntityNotFoundException(RuntimeException ex) {

        LOG.error("ERROR : {}", ex);

        return new ErrorResponse("DATA_CONSISTENCY_ERROR");

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse processException(Exception ex) {

        LOG.error("ERROR : {}", ex);

        return new ErrorResponse("GENERIC_SERVER_EXCEPTION");

    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse processRuntimeException(RuntimeException ex) {

        LOG.error("ERROR : {}", ex);

        return new ErrorResponse("INTERNAL_SERVER_ERROR");

    }

    @ExceptionHandler(value = { DomainException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse processServiceExceptionException(DomainException ex) {

        LOG.error("ERROR : {}", ex);

        return new ErrorResponse(ex.toString());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse processValidationError(MethodArgumentNotValidException ex) {

        LOG.error("ERROR : {}", ex);

        return new ErrorResponse("INPUT_ERROR");

    }

}
