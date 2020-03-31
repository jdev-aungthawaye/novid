package jdev.novid.model.infrastructure.aerospike;

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
@Qualifier(PersistenceQualifiers.AEROSPIKE)
public class UserAerospikeRepository implements UserRepository {

    @Autowired
    private UserRecordRepository userRecordRepository;

    @Override
    public void save(User domain) {

        UserRecord state = new UserRecord();

        UserRecord.map(domain, state);

        this.userRecordRepository.save(state);

    }

    @Override
    public void delete(UserId id) {

        this.userRecordRepository.delete(id);

    }

    @Override
    public User get(UserId id) {

        return User.Builder.fromState(this.userRecordRepository.get(id));

    }

    @Override
    public Optional<User> find(Mobile mobile) {

        Optional<UserRecord> optUserRecord = this.userRecordRepository.find(mobile);

        return optUserRecord.isPresent() ? Optional.of(User.Builder.fromState(optUserRecord.get())) : Optional.empty();

    }

    @Override
    public Optional<User> findById(UserId id) {

        Optional<UserRecord> optUserRecord = this.userRecordRepository.findById(id);

        if (optUserRecord.isPresent()) {

            return Optional.of(User.Builder.fromState(optUserRecord.get()));

        }

        return Optional.empty();

    }

}
