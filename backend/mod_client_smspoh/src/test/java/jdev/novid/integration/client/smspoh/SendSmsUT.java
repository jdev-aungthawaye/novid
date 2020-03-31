package jdev.novid.integration.client.smspoh;

import java.io.IOException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jdev.novid.common.value.Mobile;
import jdev.novid.component.config.ComponentConfiguration;
import jdev.novid.component.rest.AuthenticationException;
import jdev.novid.component.rest.BadRequestException;
import jdev.novid.component.rest.ResourceNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SmsPohConfiguration.class, ComponentConfiguration.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SendSmsUT {

    @Autowired
    private SmsPohClient smsPohClient;

    @Test
    public void test_sendSMS()
            throws AuthenticationException, BadRequestException, ResourceNotFoundException, IOException {

        this.smsPohClient.sendSms(new Mobile("09777773363"), "From Novid app.");

    }

}