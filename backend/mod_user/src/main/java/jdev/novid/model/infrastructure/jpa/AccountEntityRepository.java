package jdev.novid.model.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jdev.novid.common.identity.UserId;

@Repository
public interface AccountEntityRepository extends JpaRepository<AccountEntity, UserId>{

}
