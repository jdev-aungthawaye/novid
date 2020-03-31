package jdev.novid.web.api.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdev.novid.common.identity.UserId;
import jdev.novid.model.domain.Account;
import jdev.novid.model.query.UserQuery;

public class ApiAuthenticator implements Authenticator {

    private final static Logger LOG = LogManager.getLogger(ApiAuthenticator.class);

    private UserQuery userQuery;

    public ApiAuthenticator(UserQuery userQuery) {

        this.userQuery = userQuery;

    }

    @Override
    public void authenticate(UserId userId, String token) throws AuthenticationException, RequestExpiredException {

        Account account = this.userQuery.getAccount(userId);

        if (!account.isValidToken(token)) {

            throw new AuthenticationException();

        }

    }

}
