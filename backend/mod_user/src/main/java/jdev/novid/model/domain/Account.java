package jdev.novid.model.domain;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import jdev.novid.common.identity.UserId;
import jdev.novid.component.security.JwtTokenUtil;
import jdev.novid.model.infrastructure.aerospike.AccountRecord;
import jdev.novid.model.infrastructure.jpa.AccountEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

    public static class Builder {

        protected static Account newInstance(User user) {

            return new Account(user.getUserId());

        }

        public static Account fromState(AccountEntity state) {

            Account domain = new Account();

            domain.userId = state.getUserId();
            domain.secretKey = state.getSecretKey();
            domain.keygenDate = state.getKeygenDate();

            return domain;

        }

        public static Account fromState(AccountRecord state) {

            Account domain = new Account();

            domain.userId = state.getUserId();
            domain.secretKey = state.getSecretKey();
            domain.keygenDate = state.getKeygenDate();

            return domain;

        }

    }

    protected UserId userId;

    protected String secretKey;

    protected LocalDateTime keygenDate;

    public Account(UserId userId) {

        super();

        this.userId = userId;
        this.secretKey = UUID.randomUUID().toString();
        this.keygenDate = LocalDateTime.now(ZoneId.systemDefault());

    }

    protected void renew() {

        this.secretKey = UUID.randomUUID().toString();

        this.keygenDate = LocalDateTime.now(ZoneId.systemDefault());

    }

    public boolean isValidToken(String token) {

        String userIdFromToken = JwtTokenUtil.getUsernameFromToken(token, this.secretKey);

        if (userIdFromToken != null && userIdFromToken.equals(Long.toString(this.userId.getId()))) {

            return true;

        }

        return false;

    }

}
