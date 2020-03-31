package jdev.novid.integration.client.smspoh;

import java.util.Calendar;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import jdev.novid.component.rest.RestApi;
import lombok.Getter;
import retrofit2.Call;

@Component
class SendSms extends RestApi<SmsPohService, SendSms.Request, SendSms.Response> {

    @Getter
    public static class Data {

        private Message[] messages;

        private Integer balance;

        private Integer credit;

    }

    @Getter
    public static class Message {

        private Integer id;

        @JsonProperty("create_at")
        private Calendar createdAt;

        @JsonProperty("message_to")
        private String messageTo;

        @JsonProperty("message_text")
        private String messageText;

        @JsonProperty("operator")
        private String operator;

        private String sender;

        @JsonProperty("is_delivered")
        private boolean delivered;

        @JsonProperty("is_queuing")
        private boolean queuing;

        private boolean test;

        @JsonProperty("num_parts")
        private Integer numParts;

        @JsonProperty("credit")
        private Integer credit;

    }

    @Getter
    public static class Request {

        private String to;

        private String message;

        private String sender;

        public Request(String to, String message, String sender) {

            super();
            this.to = to;
            this.message = message;
            this.sender = sender;

        }

    }

    @Getter
    public static class Response {

        private boolean status;

        private Data data;

    }

    private static final Logger LOG = LogManager.getLogger(SendSms.class);

    @Autowired
    private SmsPohServiceContext smsPohServiceContext;

    @Override
    protected SmsPohService getService() {

        LOG.debug("this.smsPohServiceContext : {}", this.smsPohServiceContext);

        return this.smsPohServiceContext.getSmsPohService();

    }

    @Override
    protected Call<Response> call(Request request, Map<String, Object> extra) {

        return this.getService().sendSms(request);

    }

}
