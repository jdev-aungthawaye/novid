package jdev.novid.integration.client.smspoh;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdev.novid.component.rest.RetrofitService;
import lombok.Getter;

public class SmsPohServiceContext {

    private static final Logger LOG = LogManager.getLogger(SmsPohServiceContext.class);

    @Getter
    private SmsPohService smsPohService;

    public SmsPohServiceContext(final String baseUrl, final String bearerToken) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + bearerToken);

        this.smsPohService = new RetrofitService<SmsPohService>(SmsPohService.class, baseUrl, headers, true)
                .getService();

        LOG.debug("Configured smsPohService : {}", this.smsPohService);

    }

}
