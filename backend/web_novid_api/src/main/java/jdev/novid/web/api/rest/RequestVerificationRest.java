package jdev.novid.web.api.rest;

import java.io.Serializable;

import javax.validation.Valid;
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
import jdev.novid.model.domain.exception.MobileAlreadyTakenException;
import jdev.novid.model.usecase.RequestVerification;
import jdev.novid.support.verification.exception.CodeRequestRejectedException;
import jdev.novid.support.verification.exception.TooManyRequestsException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@RestController
public class RequestVerificationRest {

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

    }

    @Autowired
    private RequestVerification requestVerification;

    @RequestMapping(value = "/public/request-verification", method = { RequestMethod.POST })
    public ResponseEntity<RequestVerificationRest.Response> execute(
            @Valid @RequestBody RequestVerificationRest.Request request)
            throws TooManyRequestsException, CodeRequestRejectedException, MobileAlreadyTakenException {

        RequestVerification.Input input = new RequestVerification.Input(new Mobile(request.mobile));

        RequestVerification.Output output = this.requestVerification.execute(input);

        return new ResponseEntity<RequestVerificationRest.Response>(new Response(output.getResult()), HttpStatus.OK);

    }

}
