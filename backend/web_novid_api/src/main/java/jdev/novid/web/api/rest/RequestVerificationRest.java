package jdev.novid.web.api.rest;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.policy.Principal;

import jdev.novid.common.value.Mobile;
import jdev.novid.component.ddd.Result;
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

    @RequestMapping(value = "/private/purchase-main-balance", method = { RequestMethod.POST })
    public ResponseEntity<Response> execute(@Valid @RequestBody Request request, Principal principal) {

        return new ResponseEntity<RequestVerificationRest.Response>(new Response(Result.SUCCESS), HttpStatus.OK);

    }

}
