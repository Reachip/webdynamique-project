package fr.cpe.scoobygang.atelier2.filter;

import fr.cpe.scoobygang.atelier2.filter.utils.WebUtils;
import fr.cpe.scoobygang.atelier2.security.JWT;
import fr.cpe.scoobygang.atelier2.security.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static fr.cpe.scoobygang.atelier2.filter.utils.WebUtils.AUTHORIZATION_HEADER_NAME;

@Component
public class AuthorizationFilter extends GenericFilterBean {
    private final JWTService jwtService;

    public AuthorizationFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Map<String, String> headers = WebUtils.getHeadersInfo((HttpServletRequest)servletRequest);
        String bearer = headers.get(AUTHORIZATION_HEADER_NAME);

        if (bearer != null) {
            Optional<JWT> optionalJWT = jwtService.fromAuthorization(bearer);

            if (optionalJWT.isEmpty()) {
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
