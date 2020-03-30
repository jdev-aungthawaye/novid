package jdev.novid.model.infrastructure.aerospike;

import java.io.Serializable;
import java.time.LocalDateTime;

import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.Nric;
import jdev.novid.component.asmapper.annotation.AerospikeBin;
import jdev.novid.component.asmapper.annotation.AerospikeRecord;
import jdev.novid.component.persistence.jpa.TimestampConverter;
import jdev.novid.model.domain.Account;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AerospikeRecord
@Getter
@EqualsAndHashCode(callSuper = false)
public class AccountRecord implements Serializable {

    public static void map(Account domain, AccountRecord state) {

        state.userId = domain.getUserId();
        state.secretKey = domain.getSecretKey();
        state.keygenDate = domain.getKeygenDate();

    }

    /**
     * 
     */

    private static final long serialVersionUID = 1L;

    @AerospikeBin(name = "u_id", converter = UserId.AerospikeConverter.class)
    protected UserId userId;

    @AerospikeBin(name = "scrt_key")
    protected String secretKey;

    @AerospikeBin(name = "nric", converter = Nric.AerospikeConverter.class)
    protected Nric nric;

    @AerospikeBin(name = "kegen_dt", converter = TimestampConverter.class)
    protected LocalDateTime keygenDate;

}
