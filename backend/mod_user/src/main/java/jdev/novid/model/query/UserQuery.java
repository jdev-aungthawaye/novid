package jdev.novid.model.query;

import java.util.Optional;

import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.Mobile;
import jdev.novid.model.domain.Account;
import jdev.novid.model.domain.User;

public interface UserQuery {

    Optional<User> findUser(Mobile mobile);

    User getUser(UserId userId);

    Account getAccount(UserId userId);

}
