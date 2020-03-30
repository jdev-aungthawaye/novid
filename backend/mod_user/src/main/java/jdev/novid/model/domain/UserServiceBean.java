package jdev.novid.model.domain;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.Mobile;
import jdev.novid.component.persistence.PersistenceQualifiers;
import jdev.novid.model.domain.exception.MobileAlreadyTakenException;
import jdev.novid.model.infrastructure.AccountRepository;
import jdev.novid.model.infrastructure.UserRepository;

@Service
public class UserServiceBean implements UserService {

    @Autowired
    @Qualifier(PersistenceQualifiers.PROXIED)
    private UserRepository userRepository;

    @Autowired
    @Qualifier(PersistenceQualifiers.JPA)
    private AccountRepository accountRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createUser(Mobile mobile, String name, String nric) throws MobileAlreadyTakenException {

        Optional<User> optUser = this.userRepository.find(mobile);

        if (optUser.isPresent()) {

            throw new MobileAlreadyTakenException();

        }

        User user = User.Builder.newInstance(mobile, name, nric);

        this.userRepository.save(user);

        return user;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Account createAccount(User user) {

        Account account = Account.Builder.newInstance(user);

        this.accountRepository.save(account);

        return account;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Account renew(UserId userId) {

        Account account = this.accountRepository.get(userId);

        account.renew();

        this.accountRepository.save(account);

        return account;

    }

}
