package jdev.novid.support.helper;

import jdev.novid.common.value.Mobile;

public interface SmsSender {

    public void send(Mobile mobile, String message);
}
