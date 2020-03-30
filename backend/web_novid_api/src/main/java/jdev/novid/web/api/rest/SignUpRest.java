package jdev.novid.web.api.rest;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
import jdev.novid.common.value.Nric;
import jdev.novid.component.ddd.Result;
import jdev.novid.model.domain.exception.MobileAlreadyTakenException;
import jdev.novid.model.usecase.SignUp;
import jdev.novid.support.verification.exception.CodeAlreadyExpiredException;
import jdev.novid.support.verification.exception.TooManyAttemptsException;
import jdev.novid.support.verification.exception.VerificationNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@RestController
public class SignUpRest {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Pattern(regexp = Mobile.FORMAT)
        protected String mobile;

        @NotNull
        @NotBlank
        protected String name;

        @NotNull
        @NotBlank
        protected String nric;

        @NotNull
        @NotBlank
        @Pattern(regexp = "^\\d{6}$")
        protected String code;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        protected Result result;

        protected String token;

    }

    @Autowired
    private SignUp signUp;

    @RequestMapping(value = "/public/sign-up", method = { RequestMethod.POST })
    public ResponseEntity<SignUpRest.Response> execute(@Valid @RequestBody SignUpRest.Request request)
            throws MobileAlreadyTakenException, VerificationNotFoundException, TooManyAttemptsException,
            CodeAlreadyExpiredException {

        SignUp.Input input = new SignUp.Input(new Mobile(request.mobile), request.name, new Nric(request.nric),
                request.code);

        SignUp.Output output = this.signUp.execute(input);

        return new ResponseEntity<SignUpRest.Response>(new SignUpRest.Response(output.getResult(), output.getToken()),
                HttpStatus.OK);

    }

}
