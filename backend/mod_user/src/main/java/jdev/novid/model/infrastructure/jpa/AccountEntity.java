package jdev.novid.model.infrastructure.jpa;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import jdev.novid.common.identity.UserId;
import jdev.novid.component.persistence.jpa.JpaEntity;
import jdev.novid.component.persistence.jpa.TimestampConverter;
import jdev.novid.model.domain.Account;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bis_account")
@Data
@Setter(value = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class AccountEntity extends JpaEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static void map(Account domain, AccountEntity state) {

        state.userId = domain.getUserId();
        state.secretKey = domain.getSecretKey();
        state.keygenDate = domain.getKeygenDate();

    }

    @EmbeddedId
    protected UserId userId;

    @Column(name = "secret_key")
    protected String secretKey;

    @Column(name = "keygen_date")
    @Convert(converter = TimestampConverter.class)
    protected LocalDateTime keygenDate;

}
