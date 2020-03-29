package jdev.novid.support.verification.infrastructure.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jdev.novid.common.identity.VerificationId;
import jdev.novid.common.value.Mobile;

@Repository
public interface VerificationEntityRepository extends JpaRepository<VerificationEntity, VerificationId> {

    Optional<VerificationEntity> findByMobile(Mobile mobile);

}
