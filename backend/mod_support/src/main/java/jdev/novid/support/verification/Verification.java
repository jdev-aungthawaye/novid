package jdev.novid.support.verification;

import java.time.LocalDateTime;

import jdev.novid.common.identity.VerificationId;
import jdev.novid.common.value.Mobile;
import jdev.novid.component.util.BeanMapper;
import jdev.novid.component.util.SpringContext;
import jdev.novid.support.verification.exception.CodeAlreadyExpiredException;
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

        public static Verification fromState(VerificationEntity entity) {

            BeanMapper beanMapper = SpringContext.getBean(BeanMapper.class);

            return beanMapper.map(entity, Verification.class);

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

    protected Integer attempt;

    protected Integer requestCount = 0;

    protected LocalDateTime lockedUntil;

    protected LocalDateTime expiredAt;

    public Verification(Mobile mobile) {

        super();

        this.mobile = mobile;

    }

    protected String request() throws TooManyRequestsException {

        if (++this.requestCount > MAX_REQ_COUNT) {

            this.lockedUntil = LocalDateTime.now().plusDays(ONE_DAY);

            throw new TooManyRequestsException();

        }

        this.code = Integer.toString(this.generateCode(MIN, MAX));

        return this.code;

    }

    protected boolean verify(String code) throws TooManyAttemptsException, CodeAlreadyExpiredException {

        if (++this.attempt > MAX_ATTEMPT) {

            this.lockedUntil = LocalDateTime.now().plusDays(ONE_DAY);

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
