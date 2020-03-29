package jdev.novid.model.domain;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

import jdev.novid.common.identity.UserId;
import jdev.novid.component.security.JwtTokenUtil;
import lombok.Getter;

@Getter
public class AccessToken {

    public static AccessToken generate(Account account) {

        return generateToken(account);

    }

    protected UserId userId;

    protected String token;

    protected LocalDateTime expiryDate;

    private AccessToken(UserId userId, String token, LocalDateTime expiryDate) {

        super();

        this.userId = userId;
        this.token = token;
        this.expiryDate = expiryDate;

    }

    private static AccessToken generateToken(Account account) {

        final int ONE_YEAR = 1; // 365 days

        LocalDateTime expiredAt = LocalDateTime.now().plusYears(ONE_YEAR);

        final String AUDIENCE = "USER";

        String jwtToken = JwtTokenUtil.generateToken(Long.toString(account.userId.getId()), account.secretKey,
                Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()), AUDIENCE);

        return new AccessToken(account.userId, jwtToken, expiredAt);

    }

}
