package jdev.novid.support.verification;

import java.time.LocalDateTime;

import jdev.novid.common.identity.VerificationId;
import jdev.novid.common.value.Mobile;
import jdev.novid.component.ddd.Snowflake;
import jdev.novid.support.verification.exception.CodeAlreadyExpiredException;
import jdev.novid.support.verification.exception.CodeRequestRejectedException;
import jdev.novid.support.verification.exception.TooManyAttemptsException;
import jdev.novid.support.verification.exception.TooManyRequestsException;
import jdev.novid.support.verification.infrastructure.jpa.VerificationEntity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Verification {

    public static class Builder {

        protected static Verification mobile(Mobile mobile) {

            return new Verification(mobile);

        }

        public static Verification fromState(VerificationEntity state) {

            Verification domain = new Verification();

            domain.verificationId = state.getVerificationId();
            domain.mobile = state.getMobile();
            domain.code = state.getCode();
            domain.attempt = state.getAttempt();
            domain.requestCount = state.getRequestCount();
            domain.lockedUntil = state.getLockedUntil();
            domain.expiredAt = state.getExpiredAt();

            return domain;

        }

    }

    private static final int MIN = 100000;

    private static final int MAX = 999999;

    private static final int MAX_ATTEMPT = 3;

    private static final int MAX_REQ_COUNT = 5;

    private static final int ONE_DAY = 1;

    protected VerificationId verificationId;

    protected Mobile mobile;

    protected String code;

    protected Integer attempt = 0;

    protected Integer requestCount = 0;

    protected LocalDateTime lockedUntil;

    protected LocalDateTime expiredAt;

    public Verification(Mobile mobile) {

        super();

        this.verificationId = new VerificationId(Snowflake.get().nextId());

        this.mobile = mobile;

    }

    protected String request() throws TooManyRequestsException, CodeRequestRejectedException {

        if (this.lockedUntil == null) {

            if (++this.requestCount > MAX_REQ_COUNT) {

                this.lockedUntil = LocalDateTime.now().plusDays(ONE_DAY);

                throw new TooManyRequestsException();

            }

        } else {

            if (LocalDateTime.now().isAfter(this.lockedUntil)) {

                this.requestCount = 1;

                this.lockedUntil = null;

            } else {

                throw new CodeRequestRejectedException();

            }

        }

        this.code = Integer.toString(this.generateCode(MIN, MAX));

        this.expiredAt = LocalDateTime.now().plusMinutes(15);

        this.attempt = 0;

        return this.code;

    }

    protected boolean verify(String code) throws TooManyAttemptsException, CodeAlreadyExpiredException {

        if (++this.attempt > MAX_ATTEMPT) {

            throw new TooManyAttemptsException();

        }

        if (LocalDateTime.now().isAfter(this.expiredAt)) {

            throw new CodeAlreadyExpiredException();

        }

        return this.code.equals(code);

    }

    private int generateCode(int min, int max) {

        int range = (max - min) + 1;

        return (int) (Math.random() * range) + min;

    }

}
