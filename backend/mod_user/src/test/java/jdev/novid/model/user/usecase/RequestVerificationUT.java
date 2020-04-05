package jdev.novid.model.user.usecase;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jdev.novid.common.value.Mobile;
import jdev.novid.foundation.config.ApplicationConfiguration;
import jdev.novid.model.domain.exception.MobileAlreadyTakenException;
import jdev.novid.model.usecase.RequestVerification;
import jdev.novid.support.verification.exception.CodeRequestRejectedException;
import jdev.novid.support.verification.exception.TooManyRequestsException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfiguration.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RequestVerificationUT {

    @Autowired
    private RequestVerification requestVerification;

    @Test
    public void test_execute()
            throws MobileAlreadyTakenException, TooManyRequestsException, CodeRequestRejectedException {

        this.requestVerification.execute(new RequestVerification.Input(new Mobile("09987654321")));

    }

}
