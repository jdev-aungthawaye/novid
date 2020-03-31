package jdev.novid.component.persistence.jpa;

import java.util.Optional;

public interface BasicRepository<DOMAIN, ID> {

    public void save(DOMAIN domain);

    public void delete(ID id);

    public DOMAIN get(ID id);
    
    public Optional<DOMAIN> findById(ID id);

}
