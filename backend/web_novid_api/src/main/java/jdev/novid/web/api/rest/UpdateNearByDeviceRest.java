package jdev.novid.web.api.rest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
import jdev.novid.model.radar.usecase.UpdateNearByDevice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@RestController
public class UpdateNearByDeviceRest {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @NotNull
        @NotBlank
        protected String self;

        @NotNull
        @NotBlank
        protected String nearBy;

        @NotNull
        @NotBlank
        protected String deviceName;

        protected BigDecimal lat;

        protected BigDecimal lng;

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
    private UpdateNearByDevice updateNearByDevice;

    @RequestMapping(value = "/private/update-near-by-device", method = { RequestMethod.POST })
    public ResponseEntity<UpdateNearByDeviceRest.Response> execute(
            @Valid @RequestBody UpdateNearByDeviceRest.Request request, Principal principal) {

        String userId = principal.getName();

        UpdateNearByDevice.Input input = new UpdateNearByDevice.Input(new UserId(Long.valueOf(userId)),
                new MacAddress(request.self), new MacAddress(request.nearBy), request.deviceName, request.lat,
                request.lng, request.collectedAt);

        UpdateNearByDevice.Output output = this.updateNearByDevice.execute(input);

        return new ResponseEntity<UpdateNearByDeviceRest.Response>(
                new UpdateNearByDeviceRest.Response(output.getResult(), LocalDateTime.now()), HttpStatus.OK);

    }

}
