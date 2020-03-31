package jdev.novid.web.api.rest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.MacAddress;
import jdev.novid.component.ddd.Result;
import jdev.novid.component.rest.LocalDateTimeFromLong;
import jdev.novid.component.rest.LocalDateTimeToLong;
import jdev.novid.model.radar.usecase.UpdateLocation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@RestController
public class UpdateLocationRest {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        protected BigDecimal lat;

        protected BigDecimal lng;

        protected String mac;

        @NotNull
        @PastOrPresent
        @JsonDeserialize(using = LocalDateTimeFromLong.class)
        protected LocalDateTime collectedAt;

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

        @JsonSerialize(using = LocalDateTimeToLong.class)
        protected LocalDateTime receivedAt;

    }

    @Autowired
    private UpdateLocation updateLocation;

    @RequestMapping(value = "/private/update-location", method = { RequestMethod.POST })
    public ResponseEntity<UpdateLocationRest.Response> execute(@Valid @RequestBody UpdateLocationRest.Request request,
            Principal principal) {

        String userId = principal.getName();

        UpdateLocation.Input input = new UpdateLocation.Input(new UserId(Long.valueOf(userId)), request.lat,
                request.lng, new MacAddress(request.mac), request.collectedAt);

        UpdateLocation.Output output = this.updateLocation.execute(input);

        return new ResponseEntity<UpdateLocationRest.Response>(
                new UpdateLocationRest.Response(output.getResult(), LocalDateTime.now()), HttpStatus.OK);

    }

}
