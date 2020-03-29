package jdev.novid.model.usecase;

import java.io.Serializable;

import jdev.novid.common.value.Mobile;
import jdev.novid.component.ddd.Result;
import jdev.novid.model.domain.exception.MobileAlreadyTakenException;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface RequestVerification {

    @Getter
    @AllArgsConstructor
    public static class Input implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        protected Mobile mobile;

    }

    @Getter
    @AllArgsConstructor
    public static class Output implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        protected Result result;

    }

    public Output execute(Input input) throws MobileAlreadyTakenException;
}
