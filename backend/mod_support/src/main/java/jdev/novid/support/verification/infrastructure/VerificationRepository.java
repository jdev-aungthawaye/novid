package jdev.novid.support.verification.infrastructure;

import java.util.Optional;

import jdev.novid.common.identity.VerificationId;
import jdev.novid.common.value.Mobile;
import jdev.novid.component.persistence.jpa.BasicRepository;
import jdev.novid.support.verification.Verification;

public interface VerificationRepository extends BasicRepository<Verification, VerificationId> {

    Optional<Verification> find(Mobile mobile);

}
