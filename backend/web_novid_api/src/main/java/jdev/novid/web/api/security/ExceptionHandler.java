package jdev.novid.web.api.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;

import jdev.novid.component.rest.RestExceptionHandler.ErrorResponse;

public class ExceptionHandler extends OncePerRequestFilter {

    private static final Logger LOG = LogManager.getLogger(ExceptionHandler.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {

            LOG.debug("inside Exception Handler");
            filterChain.doFilter(request, response);
            LOG.debug("finish : inside Exception Handler");

        } catch (AuthenticationException exception) {

            LOG.error(exception);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Gson gson = new Gson();
            ErrorResponse errorResponse = new ErrorResponse("AUTHENTICATION_EXCEPTION");
            String json = gson.toJson(errorResponse);

            response.getWriter().write(json);

        } catch (RequestExpiredException exception) {

            LOG.error(exception);

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Gson gson = new Gson();
            ErrorResponse errorResponse = new ErrorResponse("REQUEST_EXPIRED_EXCEPTION");
            String json = gson.toJson(errorResponse);

            response.getWriter().write(json);

        } catch (Exception exception) {

            LOG.error(exception);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Gson gson = new Gson();
            ErrorResponse errorResponse = new ErrorResponse("UNEXPECTED_SERVER_ERROR");
            String json = gson.toJson(errorResponse);

            response.getWriter().write(json);

        }

    }

}