package jdev.novid.support.verification.infrastructure.jpa;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import jdev.novid.common.identity.VerificationId;
import jdev.novid.common.value.Mobile;
import jdev.novid.component.persistence.jpa.JpaEntity;
import jdev.novid.component.persistence.jpa.TimestampConverter;
import jdev.novid.support.verification.Verification;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sys_verification")
@Data
@Setter(value = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class VerificationEntity extends JpaEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static void map(Verification domain, VerificationEntity state) {

        state.verificationId = domain.getVerificationId();
        state.mobile = domain.getMobile();
        state.code = domain.getCode();
        state.attempt = domain.getAttempt();
        state.requestCount = domain.getRequestCount();
        state.lockedUntil = domain.getLockedUntil();
        state.expiredAt = domain.getExpiredAt();

    }

    @EmbeddedId
    protected VerificationId verificationId;

    @Column(name = "mobile")
    @Convert(converter = Mobile.JpaConverter.class)
    protected Mobile mobile;

    @Column(name = "code")
    protected String code;

    @Column(name = "attempt")
    protected Integer attempt;

    @Column(name = "request_count")
    protected Integer requestCount = 0;

    @Column(name = "locked_until")
    @Convert(converter = TimestampConverter.class)
    protected LocalDateTime lockedUntil;

    @Column(name = "expired_at")
    @Convert(converter = TimestampConverter.class)
    protected LocalDateTime expiredAt;

}
