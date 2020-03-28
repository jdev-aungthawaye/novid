package jdev.novid.support.verification;

import jdev.novid.common.identity.VerificationId;
import jdev.novid.common.value.Mobile;

public class Verification {

    public static class Builder {

        protected static Verification mobile(Mobile mobile) {

            return null;

        }

    }

    protected VerificationId verificationId;

    protected Mobile mobile;
    
    protected String code;
    
    protected Integer attempt;

}
