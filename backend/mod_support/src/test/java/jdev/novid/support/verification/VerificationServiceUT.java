package jdev.novid.support.verification;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jdev.novid.common.value.Mobile;
import jdev.novid.component.util.EnvAwareUnitTest;
import jdev.novid.foundation.config.ApplicationConfiguration;
import jdev.novid.support.verification.exception.CodeAlreadyExpiredException;
import jdev.novid.support.verification.exception.CodeRequestRejectedException;
import jdev.novid.support.verification.exception.TooManyAttemptsException;
import jdev.novid.support.verification.exception.TooManyRequestsException;
import jdev.novid.support.verification.exception.VerificationNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfiguration.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VerificationServiceUT extends EnvAwareUnitTest {

    @Autowired
    private VerificationService verificationService;

    @Test
    public void test_requestForSignUp() throws TooManyRequestsException, CodeRequestRejectedException {

        this.verificationService.requestForSignUp(new Mobile("09777773363"));

    }

    @Test
    @Ignore
    public void test_verify()
            throws VerificationNotFoundException, TooManyAttemptsException, CodeAlreadyExpiredException {

        boolean valid = this.verificationService.verify(new Mobile("09777773363"), "622808");
        
        System.out.print(valid);

    }

}
