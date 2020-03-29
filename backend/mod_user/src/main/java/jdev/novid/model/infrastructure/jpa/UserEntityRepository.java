package jdev.novid.model.infrastructure.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.Mobile;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, UserId> {

    Optional<UserEntity> findByMobile(Mobile mobile);
}
