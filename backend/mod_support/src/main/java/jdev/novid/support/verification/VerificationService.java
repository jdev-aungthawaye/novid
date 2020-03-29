package jdev.novid.support.verification;

import jdev.novid.common.value.Mobile;

public interface VerificationService {

    public void request(Mobile mobile);

    public void verify(Mobile mobile, String code);

}
