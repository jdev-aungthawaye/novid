package jdev.novid.support.verification;

import java.text.MessageFormat;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jdev.novid.common.value.Mobile;
import jdev.novid.component.persistence.PersistenceQualifiers;
import jdev.novid.support.helper.SmsSender;
import jdev.novid.support.verification.exception.CodeAlreadyExpiredException;
import jdev.novid.support.verification.exception.CodeRequestRejectedException;
import jdev.novid.support.verification.exception.TooManyAttemptsException;
import jdev.novid.support.verification.exception.TooManyRequestsException;
import jdev.novid.support.verification.exception.VerificationNotFoundException;
import jdev.novid.support.verification.infrastructure.VerificationRepository;

@Service
public class VerificationServiceBean implements VerificationService {

    @Autowired
    @Qualifier(PersistenceQualifiers.JPA)
    private VerificationRepository verificationRepository;

    @Autowired
    private SmsSender smsSender;

    @Override
    @Transactional(
        rollbackFor = Exception.class,
        noRollbackFor = { TooManyRequestsException.class, CodeRequestRejectedException.class })
    public void requestForSignUp(Mobile mobile) throws TooManyRequestsException, CodeRequestRejectedException {

        Optional<Verification> optVerification = this.verificationRepository.find(mobile);

        Verification verification = optVerification.isPresent() ? optVerification.get()
                : Verification.Builder.mobile(mobile);

        try {

            String otp = verification.request();

            final String message = MessageFormat.format("OTP code for sign up : {0}", otp);

            this.smsSender.send(mobile, message);

        } finally {

            this.verificationRepository.save(verification);

        }

    }

    @Override
    @Transactional(
        rollbackFor = Exception.class,
        noRollbackFor = { TooManyAttemptsException.class, CodeAlreadyExpiredException.class })
    public boolean verify(Mobile mobile, String code)
            throws VerificationNotFoundException, TooManyAttemptsException, CodeAlreadyExpiredException {

        Optional<Verification> optVerification = this.verificationRepository.find(mobile);

        if (!optVerification.isPresent()) {

            throw new VerificationNotFoundException();

        }

        Verification verification = optVerification.get();

        boolean valid = false;

        try {

            valid = verification.verify(code);

            if (valid) {

                this.verificationRepository.delete(verification.getVerificationId());

            } else {

                this.verificationRepository.save(verification);

            }

            return valid;

        } catch (TooManyAttemptsException | CodeAlreadyExpiredException e) {

            this.verificationRepository.save(verification);

            throw e;

        }

    }

    @Override
    @Transactional(
        rollbackFor = Exception.class,
        noRollbackFor = { TooManyRequestsException.class, CodeRequestRejectedException.class })
    public void requestForSignIn(Mobile mobile) throws TooManyRequestsException, CodeRequestRejectedException {

        Optional<Verification> optVerification = this.verificationRepository.find(mobile);

        Verification verification = optVerification.isPresent() ? optVerification.get()
                : Verification.Builder.mobile(mobile);

        try {

            String otp = verification.request();

            final String message = MessageFormat.format("OTP code for login : {0}", otp);

            this.smsSender.send(mobile, message);

        } finally {

            this.verificationRepository.save(verification);

        }

    }

}
