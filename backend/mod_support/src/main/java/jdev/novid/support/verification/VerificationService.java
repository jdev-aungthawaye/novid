package jdev.novid.support.verification;

import jdev.novid.common.value.Mobile;
import jdev.novid.support.verification.exception.CodeAlreadyExpiredException;
import jdev.novid.support.verification.exception.CodeRequestRejectedException;
import jdev.novid.support.verification.exception.TooManyAttemptsException;
import jdev.novid.support.verification.exception.TooManyRequestsException;
import jdev.novid.support.verification.exception.VerificationNotFoundException;

public interface VerificationService {

    public void requestForSignUp(Mobile mobile) throws TooManyRequestsException, CodeRequestRejectedException;

    public void requestForSignIn(Mobile mobile) throws TooManyRequestsException, CodeRequestRejectedException;

    public boolean verify(Mobile mobile, String code)
            throws VerificationNotFoundException, TooManyAttemptsException, CodeAlreadyExpiredException;

}
