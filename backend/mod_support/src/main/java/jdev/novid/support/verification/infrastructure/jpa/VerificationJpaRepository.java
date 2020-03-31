package jdev.novid.support.verification.infrastructure.jpa;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jdev.novid.common.identity.VerificationId;
import jdev.novid.common.value.Mobile;
import jdev.novid.component.persistence.PersistenceQualifiers;
import jdev.novid.support.verification.Verification;
import jdev.novid.support.verification.infrastructure.VerificationRepository;

@Component
@Qualifier(PersistenceQualifiers.JPA)
public class VerificationJpaRepository implements VerificationRepository {

    @Autowired
    private VerificationEntityRepository verificationEntityRepository;

    @Override
    public void save(Verification domain) {

        Optional<VerificationEntity> optEntity = this.verificationEntityRepository.findById(domain.getVerificationId());

        VerificationEntity entity = optEntity.isPresent() ? optEntity.get() : new VerificationEntity();

        VerificationEntity.map(domain, entity);

        this.verificationEntityRepository.saveAndFlush(entity);

    }

    @Override
    public void delete(VerificationId id) {

        this.verificationEntityRepository.deleteById(id);

    }

    @Override
    public Verification get(VerificationId id) {

        return Verification.Builder.fromState(this.verificationEntityRepository.getOne(id));

    }

    @Override
    public Optional<Verification> find(Mobile mobile) {

        Optional<VerificationEntity> optEntity = this.verificationEntityRepository.findByMobile(mobile);

        return optEntity.isPresent() ? Optional.of(Verification.Builder.fromState(optEntity.get())) : Optional.empty();

    }

    @Override
    public Optional<Verification> findById(VerificationId id) {

        Optional<VerificationEntity> optEntity = this.verificationEntityRepository.findById(id);

        return optEntity.isPresent() ? Optional.of(Verification.Builder.fromState(optEntity.get())) : Optional.empty();

    }

}
