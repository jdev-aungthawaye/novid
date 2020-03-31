package jdev.novid.web.api.security;

import jdev.novid.common.identity.UserId;

public interface Authenticator {

    public void authenticate(UserId userId, String token) throws AuthenticationException;

}
