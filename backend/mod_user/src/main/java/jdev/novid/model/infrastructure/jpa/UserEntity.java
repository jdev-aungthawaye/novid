package jdev.novid.model.infrastructure.jpa;

import java.time.LocalDateTime;

import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.Mobile;

public class UserEntity {

    protected UserId userId;

    protected Mobile mobile;

    protected String name;

    protected String nric;

    protected LocalDateTime registeredDate;
}
