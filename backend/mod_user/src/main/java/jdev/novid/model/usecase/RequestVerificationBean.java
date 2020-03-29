package jdev.novid.model.usecase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jdev.novid.component.ddd.Result;
import jdev.novid.model.domain.User;
import jdev.novid.model.domain.exception.MobileAlreadyTakenException;
import jdev.novid.model.query.UserQuery;
import jdev.novid.support.verification.VerificationService;

@Service
public class RequestVerificationBean implements RequestVerification {

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private UserQuery userQuery;

    @Override
    @Transactional
    public Output execute(Input input) throws MobileAlreadyTakenException {

        Optional<User> optUser = this.userQuery.find(input.mobile);

        if (optUser.isPresent()) {

            throw new MobileAlreadyTakenException();

        }

        return new Output(Result.SUCCESS);

    }

}
