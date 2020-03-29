package jdev.novid.model.domain;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import jdev.novid.common.identity.UserId;
import jdev.novid.component.security.JwtTokenUtil;
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

    protected AccessToken token() {

        return this.generateToken();

    }

    protected AccessToken refresh() {

        this.secretKey = UUID.randomUUID().toString();
        this.keygenDate = LocalDateTime.now(ZoneId.systemDefault());

        return this.generateToken();

    }

    private AccessToken generateToken() {

        final int ONE_YEAR = 1; // 365 days

        LocalDateTime expiredAt = LocalDateTime.now().plusYears(ONE_YEAR);

        final String AUDIENCE = "USER";

        String jwtToken = JwtTokenUtil.generateToken(Long.toString(this.userId.getId()), this.secretKey,
                Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()), AUDIENCE);

        return new AccessToken(this.userId, jwtToken, expiredAt);

    }

}
