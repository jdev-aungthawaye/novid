package jdev.novid.component.persistence.jpa;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import lombok.Setter;

@Setter
@MappedSuperclass
public abstract class JpaEntity implements Serializable {

    private static final long serialVersionUID = -5526155220442471285L;

    @Column(name = "created_date", updatable = false)
    @Convert(converter = TimestampConverter.class)
    protected LocalDateTime createdDate;

    @Column(name = "updated_date")
    @Convert(converter = TimestampConverter.class)
    protected LocalDateTime updatedDate;

    @Column(name = "version")
    @Version
    protected Integer version;

    @PrePersist
    public void prePersist() {

        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();

    }

    @PreUpdate
    public void preUpdate() {

        this.updatedDate = LocalDateTime.now();

    }

}
