package jdev.novid.model.radar.usecase;

import java.time.LocalDateTime;

import jdev.novid.common.identity.UserId;
import jdev.novid.component.ddd.Result;
import jdev.novid.model.domain.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface UpdateNearByUser {

    @Getter
    @AllArgsConstructor
    public static class Input {

        protected UserId sourceId;

        protected UserId nearByUserId;

        protected LocalDateTime collectedAt;

    }

    @Getter
    @AllArgsConstructor
    public static class Output {

        protected Result result;

    }

    public Output execute(Input input) throws UserNotFoundException;

}
