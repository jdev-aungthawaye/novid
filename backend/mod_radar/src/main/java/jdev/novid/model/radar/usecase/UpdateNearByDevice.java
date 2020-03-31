package jdev.novid.model.radar.usecase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jdev.novid.common.identity.UserId;
import jdev.novid.common.value.MacAddress;
import jdev.novid.component.ddd.Result;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface UpdateNearByDevice {

    @Getter
    @AllArgsConstructor
    public static class Input {

        protected UserId sourceId;

        protected MacAddress self;

        protected MacAddress nearBy;

        protected String deviceName;

        protected BigDecimal lat;

        protected BigDecimal lng;

        protected LocalDateTime collectedAt;

    }

    @Getter
    @AllArgsConstructor
    public static class Output {

        protected Result result;

    }

    public Output execute(Input input);

}
