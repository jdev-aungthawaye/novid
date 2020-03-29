package jdev.novid.model.query;

import java.util.Optional;

import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.Mobile;
import jdev.novid.model.domain.User;

public interface UserQuery {

    Optional<User> find(Mobile mobile);

    User get(UserId userId);

}
