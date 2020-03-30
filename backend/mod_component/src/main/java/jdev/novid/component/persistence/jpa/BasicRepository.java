package jdev.novid.component.persistence.jpa;

public interface BasicRepository<DOMAIN, ID> {

    public void save(DOMAIN domain);

    public void delete(ID id);

    public DOMAIN get(ID id);

}
