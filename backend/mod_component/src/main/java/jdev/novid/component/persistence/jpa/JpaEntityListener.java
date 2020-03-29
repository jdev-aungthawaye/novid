package jdev.novid.component.persistence.jpa;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class JpaEntityListener {

    @PostLoad
    public void postLoad(JpaEntity ob) {

    }

    @PostPersist
    public void postPersist(JpaEntity ob) {

    }

    @PostRemove
    public void postRemove(JpaEntity ob) {

    }

    @PostUpdate
    public void postUpdate(JpaEntity ob) {

    }

    @PrePersist
    public void prePersist(JpaEntity ob) {

        // ob.setCreatedDate(Calendar.getInstance());
        // ob.setUpdatedDate(Calendar.getInstance());
    }

    @PreRemove
    public void preRemove(JpaEntity ob) {

    }

    @PreUpdate
    public void preUpdate(JpaEntity ob) {

        // ob.setUpdatedDate(Calendar.getInstance());
    }

}
