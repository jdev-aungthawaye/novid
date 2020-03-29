package jdev.novid.support.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jdev.novid.common.value.Mobile;
import jdev.novid.component.ddd.InformationalException;
import jdev.novid.component.persistence.PersistenceQualifiers;
import jdev.novid.support.verification.infrastructure.VerificationRepository;

@Service
public class VerificationServiceBean implements VerificationService {

    @Autowired
    @Qualifier(PersistenceQualifiers.JPA)
    private VerificationRepository verificationRepository;

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = InformationalException.class)
    public void request(Mobile mobile) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = InformationalException.class)
    public void verify(Mobile mobile, String code) {

    }

}
