package jdev.novid.model.infrastructure.jpa;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.Mobile;
import jdev.novid.component.persistence.PersistenceQualifiers;
import jdev.novid.model.domain.User;
import jdev.novid.model.infrastructure.UserRepository;

@Component
@Qualifier(PersistenceQualifiers.JPA)
public class UserJpaRepository implements UserRepository {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public void save(User domain) {

        Optional<UserEntity> optEntity = this.userEntityRepository.findById(domain.getUserId());

        UserEntity entity = optEntity.isPresent() ? optEntity.get() : new UserEntity();

        UserEntity.map(domain, entity);

        this.userEntityRepository.saveAndFlush(entity);

    }

    @Override
    public void delete(UserId id) {

        this.userEntityRepository.deleteById(id);

    }

    @Override
    public User get(UserId id) {

        return User.Builder.fromState(this.userEntityRepository.getOne(id));

    }

    @Override
    public Optional<User> find(Mobile mobile) {

        Optional<UserEntity> optEntity = this.userEntityRepository.findByMobile(mobile);

        return optEntity.isPresent() ? Optional.of(User.Builder.fromState(optEntity.get())) : Optional.empty();

    }

}
