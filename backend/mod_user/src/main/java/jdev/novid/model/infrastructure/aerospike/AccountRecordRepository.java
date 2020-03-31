package jdev.novid.model.infrastructure.aerospike;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jdev.novid.common.identity.UserId;
import jdev.novid.component.asmapper.AerospikeTemplate;
import jdev.novid.component.persistence.jpa.BasicRepository;

@Component
public class AccountRecordRepository implements BasicRepository<AccountRecord, UserId> {

    private static final String SET = "account";

    @Autowired
    private AerospikeTemplate aerospikeTemplate;

    public void save(AccountRecord accountRecord) {

        this.aerospikeTemplate.save(accountRecord.getUserId().getId(), SET, accountRecord);

    }

    @Override
    public void delete(UserId id) {

        this.aerospikeTemplate.delete(id.getId(), SET);

    }

    @Override
    public AccountRecord get(UserId id) {

        Optional<AccountRecord> optional = this.aerospikeTemplate.find(id.getId(), SET, AccountRecord.class);

        if (optional.isPresent()) {

            return optional.get();

        }

        throw new EntityNotFoundException("AccountRecord with PK [" + id + "] not found in Aerospike.");

    }

    @Override
    public Optional<AccountRecord> findById(UserId id) {

        return this.aerospikeTemplate.find(id.getId(), SET, AccountRecord.class);

    }

}
