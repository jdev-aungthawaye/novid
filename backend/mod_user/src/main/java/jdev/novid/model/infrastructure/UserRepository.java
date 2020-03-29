package jdev.novid.model.infrastructure;

import java.util.Optional;

import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.Mobile;
import jdev.novid.component.persistence.jpa.BasicRepository;
import jdev.novid.model.domain.User;

public interface UserRepository extends BasicRepository<User, UserId> {

    Optional<User> find(Mobile mobile);
}
