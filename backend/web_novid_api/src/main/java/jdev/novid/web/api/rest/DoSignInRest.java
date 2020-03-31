package jdev.novid.web.api.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jdev.novid.common.value.Mobile;
import jdev.novid.component.ddd.Result;
import jdev.novid.model.domain.exception.MobileNotFoundException;
import jdev.novid.model.usecase.DoSignIn;
import jdev.novid.support.verification.exception.CodeAlreadyExpiredException;
import jdev.novid.support.verification.exception.TooManyAttemptsException;
import jdev.novid.support.verification.exception.VerificationNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
public class DoSignInRest {

    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotNull
        @NotEmpty
        @Pattern(regexp = Mobile.FORMAT)
        public String mobile;

        @NotNull
        @NotEmpty
        @Pattern(regexp = "^\\d{6}$")
        public String code;

    }

    @AllArgsConstructor
    public static class Response {

        public Result result;

        public String token;

        public Long userId;

    }

    @Autowired
    private DoSignIn doSignIn;

    @RequestMapping(value = "/public/do-sign-in", method = { RequestMethod.POST })
    public ResponseEntity<DoSignInRest.Response> execute(@Valid @RequestBody DoSignInRest.Request request)
            throws VerificationNotFoundException, TooManyAttemptsException, CodeAlreadyExpiredException,
            MobileNotFoundException {

        DoSignIn.Input input = new DoSignIn.Input(new Mobile(request.mobile), request.code);

        DoSignIn.Output output = this.doSignIn.execute(input);

        return new ResponseEntity<DoSignInRest.Response>(
                new DoSignInRest.Response(output.getResult(), output.getToken(), output.getUserId()), HttpStatus.OK);

    }

}
