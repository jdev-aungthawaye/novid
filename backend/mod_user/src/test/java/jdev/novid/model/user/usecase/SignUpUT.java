package jdev.novid.model.user.usecase;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jdev.novid.common.value.Mobile;
import jdev.novid.common.value.Nric;
import jdev.novid.foundation.config.ApplicationConfiguration;
import jdev.novid.model.domain.exception.MobileAlreadyTakenException;
import jdev.novid.model.usecase.SignUp;
import jdev.novid.support.verification.exception.CodeAlreadyExpiredException;
import jdev.novid.support.verification.exception.TooManyAttemptsException;
import jdev.novid.support.verification.exception.VerificationNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfiguration.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpUT {

    @Autowired
    private SignUp signUp;

    @Test
    public void test_execute() throws MobileAlreadyTakenException, VerificationNotFoundException,
            TooManyAttemptsException, CodeAlreadyExpiredException {

        this.signUp
                .execute(new SignUp.Input(new Mobile("09987654321"), "JDev", new Nric("12/TaMaNa(N)123456"), "638774"));

    }

}
