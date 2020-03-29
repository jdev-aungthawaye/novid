package jdev.novid.model.infrastructure.jpa;

import java.time.LocalDateTime;

import jdev.novid.common.identity.UserId;

public class AccountEntity {

    protected UserId userId;

    protected String secretKey;

    protected LocalDateTime keygenDate;

}
