package jdev.novid.support.verification.infrastructure.jpa;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;

import jdev.novid.common.identity.VerificationId;
import jdev.novid.common.value.Mobile;
import jdev.novid.component.persistence.jpa.JpaEntity;
import jdev.novid.component.persistence.jpa.TimestampConverter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class VerificationEntity extends JpaEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected VerificationId verificationId;

    @Column(name = "mobile")
    @Convert(converter = Mobile.JpaConverter.class)
    protected Mobile mobile;

    protected String code;

    protected Integer attempt;

    protected Integer requestCount = 0;

    @Column(name = "locked_untill")
    @Convert(converter = TimestampConverter.class)
    protected LocalDateTime lockedUntil;

    @Column(name = "expired_at")
    @Convert(converter = TimestampConverter.class)
    protected LocalDateTime expiredAt;

}
