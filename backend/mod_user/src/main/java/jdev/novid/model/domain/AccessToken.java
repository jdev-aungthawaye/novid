package jdev.novid.model.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import jdev.novid.common.identity.UserId;
import lombok.Getter;

@Getter
public class AccessToken implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected UserId userId;

    protected String token;

    protected LocalDateTime expiryDate;

    public AccessToken(UserId userId, String token, LocalDateTime expiryDate) {

        super();

        this.userId = userId;
        this.token = token;
        this.expiryDate = expiryDate;

    }

}
