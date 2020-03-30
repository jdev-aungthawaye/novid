package jdev.novid.model.infrastructure.jpa;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.Mobile;
import jdev.novid.common.value.Nric;
import jdev.novid.component.persistence.jpa.JpaEntity;
import jdev.novid.component.persistence.jpa.TimestampConverter;
import jdev.novid.model.domain.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bis_user")
@Data
@Setter(value = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends JpaEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static void map(User domain, UserEntity state) {

        state.userId = domain.getUserId();
        state.mobile = domain.getMobile();
        state.name = domain.getName();
        state.nric = domain.getNric();
        state.registeredDate = domain.getRegisteredDate();

    }

    @EmbeddedId
    protected UserId userId;

    @Column(name = "mobile")
    @Convert(converter = Mobile.JpaConverter.class)
    protected Mobile mobile;

    @Column(name = "name")
    protected String name;

    @Column(name = "nric")
    @Convert(converter = Nric.JpaConverter.class)
    protected Nric nric;

    @Column(name = "registered_date")
    @Convert(converter = TimestampConverter.class)
    protected LocalDateTime registeredDate;

}
