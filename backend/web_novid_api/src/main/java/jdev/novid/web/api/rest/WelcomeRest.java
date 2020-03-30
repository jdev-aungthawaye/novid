package jdev.novid.web.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeRest {

    @RequestMapping(value = "/", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<String> execute() {

        return new ResponseEntity<String>("Welcome to Novid API", HttpStatus.OK);

    }

}
