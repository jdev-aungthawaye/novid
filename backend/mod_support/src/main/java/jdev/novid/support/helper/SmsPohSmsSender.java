package jdev.novid.support.helper;

import jdev.novid.common.value.Mobile;
import jdev.novid.integration.client.smspoh.SmsPohClient;

public class SmsPohSmsSender implements SmsSender {

    private SmsPohClient smsPohClient;

    public SmsPohSmsSender(SmsPohClient smsPohClient) {

        super();

        this.smsPohClient = smsPohClient;

    }

    @Override
    public void send(Mobile mobile, String message) {

        this.smsPohClient.sendSms(mobile, message);

    }

}
