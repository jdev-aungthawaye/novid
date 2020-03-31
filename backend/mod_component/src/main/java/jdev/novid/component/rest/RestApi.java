package jdev.novid.component.rest;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import retrofit2.Call;
import retrofit2.Response;

public abstract class RestApi<SERVICE, REQUEST, RESPONSE> {

    private static final Logger LOG = LogManager.getLogger(RestApi.class);

    public RestApi() {

    }

    public Response<RESPONSE> invoke(final REQUEST request, Map<String, Object> extra)
            throws AuthenticationException, BadRequestException, ResourceNotFoundException, IOException {

        Call<RESPONSE> call = RestApi.this.call(request, extra);

        Response<RESPONSE> response = call.execute();

        LOG.debug("call.executed : {}", call.isExecuted());

        if (response != null && !response.isSuccessful()) {

            switch (response.code()) {

                case 400:

                    LOG.debug("response [400] : response.errorBody : {}", response.errorBody().string());

                    if (response.errorBody() != null) {

                        String body = response.errorBody().string();
                        throw new BadRequestException("UNKNOWN", body);

                    }

                case 401:
                case 403:
                    LOG.debug("RestRx - call : Error occurred at rest API authentication...");
                    throw new AuthenticationException();

                case 404:
                    LOG.debug("RestRx - call : Error occurred at rest API not found...");
                    throw new ResourceNotFoundException();

            }

        }

        return response;

    }

    public Response<RESPONSE> invoke(final REQUEST request)
            throws AuthenticationException, BadRequestException, ResourceNotFoundException, IOException {

        return this.invoke(request, null);

    }

    protected abstract Call<RESPONSE> call(REQUEST request, Map<String, Object> extra);

    protected abstract SERVICE getService();

}