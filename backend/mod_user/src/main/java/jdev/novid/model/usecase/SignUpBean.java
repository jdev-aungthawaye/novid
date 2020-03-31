package jdev.novid.model.usecase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jdev.novid.component.ddd.Result;
import jdev.novid.model.domain.AccessToken;
import jdev.novid.model.domain.Account;
import jdev.novid.model.domain.User;
import jdev.novid.model.domain.UserService;
import jdev.novid.model.domain.exception.MobileAlreadyTakenException;
import jdev.novid.model.query.UserQuery;
import jdev.novid.support.verification.VerificationService;
import jdev.novid.support.verification.exception.CodeAlreadyExpiredException;
import jdev.novid.support.verification.exception.TooManyAttemptsException;
import jdev.novid.support.verification.exception.VerificationNotFoundException;

@Service
public class SignUpBean implements SignUp {

    @Autowired
    private UserService userService;

    @Autowired
    private UserQuery userQuery;

    @Autowired
    private VerificationService verificationService;

    @Override
    public Output execute(Input input) throws MobileAlreadyTakenException, VerificationNotFoundException,
            TooManyAttemptsException, CodeAlreadyExpiredException {

        Optional<User> optUser = this.userQuery.findUser(input.mobile);

        if (optUser.isPresent()) {

            throw new MobileAlreadyTakenException();

        }

        boolean valid = this.verificationService.verify(input.mobile, input.code);

        if (!valid) {

            return new Output(Result.FAIL, null, null);

        }

        User user = this.userService.createUser(input.mobile, input.name, input.nric);

        Account account = this.userService.createAccount(user);

        AccessToken token = AccessToken.generate(account);

        return new Output(Result.SUCCESS, token.getToken(), user.getUserId());

    }

}
