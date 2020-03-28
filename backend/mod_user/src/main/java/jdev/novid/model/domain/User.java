package jdev.novid.model.domain;

import java.time.LocalDateTime;

import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.Mobile;
import jdev.novid.component.ddd.Snowflake;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    public static class Builder {

        protected static User newInstance(Mobile mobile, String name, String nric) {

            return new User(mobile, name, nric);

        }

        public static User fromState() {

            return null;

        }

    }

    protected UserId userId;

    protected Mobile mobile;

    protected String name;

    protected String nric;

    protected LocalDateTime registeredDate;

    public User(Mobile mobile, String name, String nric) {

        super();

        this.userId = new UserId(Snowflake.get().nextId());
        this.mobile = mobile;
        this.name = name;
        this.nric = nric;

    }

}
