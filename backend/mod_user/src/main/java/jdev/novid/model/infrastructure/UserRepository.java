package jdev.novid.model.infrastructure;

import jdev.novid.common.identity.UserId;
import jdev.novid.component.persistence.jpa.BasicRepository;
import jdev.novid.model.domain.User;

public interface UserRepository extends BasicRepository<User, UserId> {

}
