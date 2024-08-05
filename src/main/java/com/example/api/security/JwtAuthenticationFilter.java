package com.example.api.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    private static final String[] EXCLUDED_URLS = new String[]{"/api/public/*", "/api/auth/*"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws IOException {
        final String authHeader = request.getHeader("Authorization");
        if (StringUtils.startsWith(authHeader, "Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String email = jwtTokenUtil.extractUsername(token);
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (StringUtils.isNotEmpty(email) && (SecurityContextHolder.getContext()
                        .getAuthentication() == null || (authentication instanceof AnonymousAuthenticationToken))) {
                    UserPrincipal userPrincipal = (UserPrincipal) userDetailsService.loadUserByUsername(email);
                    if (jwtTokenUtil.isTokenValid(token, userPrincipal)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userPrincipal, null,
                                                                        userPrincipal.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
                filterChain.doFilter(request, response);
            } catch (IllegalArgumentException e) {
                logger.error("Unable to fetch JWT Token");
                throw new AuthenticationException("Unable to fetch JWT Token");
            } catch (ExpiredJwtException e) {
                logger.warn("JWT Token is expired.");
                throw new AuthenticationException("JWT Token is expired.");
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new AuthenticationException(e.getMessage());
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
            throw new AuthenticationException("JWT Token does not begin with Bearer String");
        }
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return Arrays.stream(EXCLUDED_URLS).anyMatch(e -> new AntPathMatcher().match(e, request.getServletPath()));
    }
}