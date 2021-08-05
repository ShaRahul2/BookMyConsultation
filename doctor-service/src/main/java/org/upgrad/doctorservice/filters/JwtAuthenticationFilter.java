package org.upgrad.doctorservice.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.upgrad.doctorservice.model.UserPrincipal;
import org.upgrad.doctorservice.service.TokenProvider;
import org.upgrad.doctorservice.service.UserDetailsServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = httpServletRequest.getHeader("Authorization");
            if(!StringUtils.isEmpty(jwt) && tokenProvider.validateToken(jwt)) {
                String userNameInToken = tokenProvider.getUserNameFromToken(jwt);
                UserPrincipal userPrincipal = userDetailsServiceImpl.loadUserByUsername(userNameInToken);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch(Exception ex) {
            //LOGGER.error("Failed to validate the token and/or set authentication token in security context", ex);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
