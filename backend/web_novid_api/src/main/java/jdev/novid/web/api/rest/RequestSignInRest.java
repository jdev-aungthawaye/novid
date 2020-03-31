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
import jdev.novid.model.usecase.RequestSignIn;
import jdev.novid.support.verification.exception.CodeRequestRejectedException;
import jdev.novid.support.verification.exception.TooManyRequestsException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
public class RequestSignInRest {

    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotNull
        @NotEmpty
        @Pattern(regexp = Mobile.FORMAT)
        public String mobile;

    }

    @AllArgsConstructor
    public static class Response {

        public Result result;

    }

    @Autowired
    private RequestSignIn requestSignIn;

    @RequestMapping(value = "/public/request-sign-in", method = { RequestMethod.POST })
    public ResponseEntity<RequestSignInRest.Response> execute(@Valid @RequestBody RequestSignInRest.Request request)
            throws TooManyRequestsException, CodeRequestRejectedException, MobileNotFoundException {

        RequestSignIn.Input input = new RequestSignIn.Input(new Mobile(request.mobile));

        RequestSignIn.Output output = this.requestSignIn.execute(input);

        return new ResponseEntity<RequestSignInRest.Response>(new RequestSignInRest.Response(output.getResult()),
                HttpStatus.OK);

    }

}
