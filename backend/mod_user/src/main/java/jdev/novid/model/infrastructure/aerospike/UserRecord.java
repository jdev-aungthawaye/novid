package jdev.novid.model.infrastructure.aerospike;

import java.io.Serializable;
import java.time.LocalDateTime;

import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.Mobile;
import jdev.novid.component.asmapper.annotation.AerospikeBin;
import jdev.novid.component.asmapper.annotation.AerospikeRecord;
import jdev.novid.component.persistence.jpa.TimestampConverter;
import jdev.novid.model.domain.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AerospikeRecord
@Getter
@EqualsAndHashCode(callSuper = false)
public class UserRecord implements Serializable {

    public static void map(User domain, UserRecord state) {

        state.userId = domain.getUserId();
        state.mobile = domain.getMobile();
        state.name = domain.getName();
        state.nric = domain.getNric();
        state.registeredDate = domain.getRegisteredDate();

    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @AerospikeBin(name = "u_id", converter = UserId.AerospikeConverter.class)
    protected UserId userId;

    @AerospikeBin(name = "mobile", converter = Mobile.AerospikeConverter.class)
    protected Mobile mobile;

    @AerospikeBin(name = "name")
    protected String name;

    @AerospikeBin(name = "nric")
    protected String nric;

    @AerospikeBin(name = "reg_dt", converter = TimestampConverter.class)
    protected LocalDateTime registeredDate;

}
