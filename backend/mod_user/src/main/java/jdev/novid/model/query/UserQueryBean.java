package jdev.novid.model.query;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.Mobile;
import jdev.novid.component.persistence.PersistenceQualifiers;
import jdev.novid.model.domain.Account;
import jdev.novid.model.domain.User;
import jdev.novid.model.infrastructure.AccountRepository;
import jdev.novid.model.infrastructure.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserQueryBean implements UserQuery {

    @Autowired
    @Qualifier(PersistenceQualifiers.PROXIED)
    private UserRepository userRepository;

    @Autowired
    @Qualifier(PersistenceQualifiers.PROXIED)
    private AccountRepository accountRepository;

    @Override
    public Optional<User> findUser(Mobile mobile) {

        return this.userRepository.find(mobile);

    }

    @Override
    public User getUser(UserId userId) {

        return this.userRepository.get(userId);

    }

    @Override
    public Account getAccount(UserId userId) {

        return this.accountRepository.get(userId);

    }

}
