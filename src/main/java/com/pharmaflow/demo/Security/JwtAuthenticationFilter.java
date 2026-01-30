package com.pharmaflow.demo.Security;

import com.pharmaflow.demo.Services.Impl.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final RedisTemplate<String, String> redisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String email;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            token = authHeader.substring(7);

            // check is token in black list
            if (redisTemplate.hasKey("black_token::"+token)) {
                handlerExceptionResolver.resolveException(request,
                        response,
                        null,
                        new AccessDeniedException("login again please"));
                return ;
            }

            email = this.jwtService.extractUsername(token);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserSecurity userSecurity = (UserSecurity) this.userDetailsService.loadUserByUsername(email);
                userSecurity.setToken(token);
                if (jwtService.isTokenValid(token, userSecurity)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userSecurity,
                            null,
                            userSecurity.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
