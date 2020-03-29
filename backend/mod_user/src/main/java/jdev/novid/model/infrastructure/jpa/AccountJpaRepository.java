package jdev.novid.model.infrastructure.jpa;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jdev.novid.common.identity.UserId;
import jdev.novid.component.persistence.PersistenceQualifiers;
import jdev.novid.component.util.BeanMapper;
import jdev.novid.model.domain.Account;
import jdev.novid.model.infrastructure.AccountRepository;

@Component
@Qualifier(PersistenceQualifiers.JPA)
public class AccountJpaRepository implements AccountRepository {

    @Autowired
    private AccountEntityRepository accountEntityRepository;

    @Autowired
    private BeanMapper beanMapper;

    @Override
    public void save(Account domain) {

        Optional<AccountEntity> optEntity = this.accountEntityRepository.findById(domain.getUserId());

        AccountEntity entity = optEntity.isPresent() ? optEntity.get() : new AccountEntity();

        this.beanMapper.map(domain, entity);

        this.accountEntityRepository.saveAndFlush(entity);

    }

    @Override
    public void delete(UserId id) {

        this.accountEntityRepository.deleteById(id);

    }

    @Override
    public Account get(UserId id) {

        return Account.Builder.fromState(this.accountEntityRepository.getOne(id));

    }

}
