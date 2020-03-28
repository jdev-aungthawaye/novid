
package jdev.novid.component.ddd;

public interface DomainEventPublisher {

    public <E extends DomainEvent> void publish(E event);
}
