package jdev.novid.component.ddd;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;

@Getter
public class DomainEvent implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String id;

    private String eventName;

    public DomainEvent() {

        this.id = UUID.randomUUID().toString();
        this.eventName = this.getClass().getName();
    }

}