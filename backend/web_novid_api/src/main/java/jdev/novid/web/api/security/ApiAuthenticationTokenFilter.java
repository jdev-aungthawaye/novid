package jdev.novid.web.api.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jdev.novid.common.identity.UserId;

public class ApiAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger LOG = LogManager.getLogger(ApiAuthenticationTokenFilter.class);

    private static final String TOKEN_HEADER = "X-TOKEN";

    private static final String USER_ID_HEADER = "X-USER-ID";

    private Authenticator authenticator = null;

    public ApiAuthenticationTokenFilter(Authenticator authenticator) {

        super();
        this.authenticator = authenticator;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader(TOKEN_HEADER);
        String userIdString = request.getHeader(USER_ID_HEADER);

        LOG.info("token : {}", token);
        LOG.info("userId : {}", userIdString);

        if (token != null && userIdString != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserId userId = new UserId(Long.valueOf(userIdString));

            this.authenticator.authenticate(userId, token);

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userId.getId(), "N.A", authorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            LOG.debug("Authentication successful...");

        }

        filterChain.doFilter(request, response);

    }

}
