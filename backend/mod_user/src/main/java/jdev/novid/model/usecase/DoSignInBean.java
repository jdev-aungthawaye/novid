package jdev.novid.model.usecase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jdev.novid.component.ddd.Result;
import jdev.novid.model.domain.AccessToken;
import jdev.novid.model.domain.Account;
import jdev.novid.model.domain.User;
import jdev.novid.model.domain.UserService;
import jdev.novid.model.domain.exception.MobileNotFoundException;
import jdev.novid.model.query.UserQuery;
import jdev.novid.support.verification.VerificationService;
import jdev.novid.support.verification.exception.CodeAlreadyExpiredException;
import jdev.novid.support.verification.exception.TooManyAttemptsException;
import jdev.novid.support.verification.exception.VerificationNotFoundException;

@Service
public class DoSignInBean implements DoSignIn {

    @Autowired
    private UserQuery userQuery;

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationService verificationService;

    @Override
    @Transactional(
        rollbackFor = Exception.class,
        noRollbackFor = { TooManyAttemptsException.class, CodeAlreadyExpiredException.class })
    public Output execute(Input input) throws MobileNotFoundException, VerificationNotFoundException,
            TooManyAttemptsException, CodeAlreadyExpiredException {

        Optional<User> optUser = this.userQuery.findUser(input.mobile);

        if (!optUser.isPresent()) {

            throw new MobileNotFoundException();

        }

        boolean valid = this.verificationService.verify(input.mobile, input.code);

        if (!valid) {

            return new Output(Result.FAIL, null);

        }

        Account account = this.userService.renew(optUser.get().getUserId());

        return new Output(Result.SUCCESS, AccessToken.generate(account).getToken());

    }

}
