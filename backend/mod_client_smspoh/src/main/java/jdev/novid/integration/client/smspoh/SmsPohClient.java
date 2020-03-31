package jdev.novid.integration.client.smspoh;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import jdev.novid.common.value.Mobile;
import jdev.novid.component.rest.AuthenticationException;
import jdev.novid.component.rest.BadRequestException;
import jdev.novid.component.rest.ResourceNotFoundException;
import retrofit2.Response;

public class SmsPohClient {

    @Autowired
    private SendSms sendSms;

    private String senderName;

    public SmsPohClient(String senderName) {

        this.senderName = senderName;

    }

    public boolean sendSms(Mobile mobile, String message) {

        SendSms.Request request = new SendSms.Request("+95" + mobile.getValue().substring(1), message, this.senderName);

        try {

            Response<SendSms.Response> httpResponse = this.sendSms.invoke(request);

            return httpResponse.body().isStatus();

        } catch (AuthenticationException | BadRequestException | ResourceNotFoundException | IOException e) {

            return false;

        }

    }

}
