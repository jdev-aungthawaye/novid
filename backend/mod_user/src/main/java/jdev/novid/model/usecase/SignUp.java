package jdev.novid.model.usecase;

import java.io.Serializable;

import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.Mobile;
import jdev.novid.common.value.Nric;
import jdev.novid.component.ddd.Result;
import jdev.novid.model.domain.exception.MobileAlreadyTakenException;
import jdev.novid.support.verification.exception.CodeAlreadyExpiredException;
import jdev.novid.support.verification.exception.TooManyAttemptsException;
import jdev.novid.support.verification.exception.VerificationNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface SignUp {

    @Getter
    @AllArgsConstructor
    public static class Input implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        protected Mobile mobile;

        protected String name;

        protected Nric nric;

        protected String code;

    }

    @Getter
    @AllArgsConstructor
    public static class Output implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        protected Result result;

        protected String token;

        protected UserId userId;

    }

    public Output execute(Input input) throws MobileAlreadyTakenException, VerificationNotFoundException,
            TooManyAttemptsException, CodeAlreadyExpiredException;

}
