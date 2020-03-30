package jdev.novid.model.infrastructure.aerospike;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aerospike.client.query.Filter;

import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.Mobile;
import jdev.novid.component.asmapper.AerospikeTemplate;
import jdev.novid.component.persistence.jpa.BasicRepository;
import jdev.novid.model.domain.User;

@Component
public class UserRecordRepository implements BasicRepository<UserRecord, UserId> {

    private static final String SET = "user";

    @Autowired
    private AerospikeTemplate aerospikeTemplate;

    public void save(UserRecord userRecord) {

        this.aerospikeTemplate.save(userRecord.getUserId().getId(), SET, userRecord);

    }

    @Override
    public void delete(UserId id) {

        this.aerospikeTemplate.delete(id.getId(), SET);

    }

    @Override
    public UserRecord get(UserId id) {

        Optional<UserRecord> optional = this.aerospikeTemplate.find(id.getId(), SET, UserRecord.class);

        if (optional.isPresent()) {

            return optional.get();

        }

        throw new EntityNotFoundException("UserRecord with PK [" + id + "] not found in Aerospike.");

    }

    public Optional<UserRecord> find(Mobile mobile) {

        List<UserRecord> records = this.aerospikeTemplate.query(SET, Filter.equal("mobile", mobile.getValue()),
                UserRecord.class);

        if (records.isEmpty())
            return Optional.empty();

        if (records.size() > 1) {

            throw new IllegalStateException("Found more than one record. Expected only one.");

        }

        return Optional.of(records.get(0));

    }

}
