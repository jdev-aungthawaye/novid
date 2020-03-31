package jdev.novid.web.api.security;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.google.gson.Gson;

import jdev.novid.component.rest.RestExceptionHandler.ErrorResponse;

public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger(ApiAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        LOG.debug("inside AuthEntryPoint : {}", authException.getClass().getName());

        LOG.error(authException);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        ErrorResponse errorResponse = new ErrorResponse("AUTHENTICATION_EXCEPTION");
        String json = gson.toJson(errorResponse);

        response.getWriter().write(json);
    }
}