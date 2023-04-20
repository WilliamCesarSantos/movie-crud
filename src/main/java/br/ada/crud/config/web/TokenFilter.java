package br.ada.crud.config.web;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class TokenFilter implements Filter {

    private static final String SECRET_KEY = "token_jwt";
    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        log.debug("Applying token filter");
        var token = getToken((HttpServletRequest) request);
        if (token != null || skipUrl((HttpServletRequest) request)) {
            filterChain.doFilter(request, response);
        } else {
            writeUnauthorized((HttpServletResponse) response);
        }
    }

    private boolean skipUrl(HttpServletRequest request) {
        String url = request.getRequestURI();
        return url.contains("swagger-ui") || url.contains("api-docs");
    }

    private void writeUnauthorized(HttpServletResponse response) throws IOException {
        response.getWriter()
                .write("""
                        {"message": "User unauthorized"}
                        """);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String token = null;
        if (authorization != null) {
            token = authorization.substring(7);
        }
        return token;
    }
}
