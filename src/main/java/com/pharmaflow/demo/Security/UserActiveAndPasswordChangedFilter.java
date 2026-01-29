package com.pharmaflow.demo.Security;

import com.pharmaflow.demo.Entities.User;
import com.pharmaflow.demo.Exceptions.ResourceNotFoundException;
import com.pharmaflow.demo.Repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.security.auth.login.CredentialException;
import javax.security.auth.login.CredentialExpiredException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
@RequiredArgsConstructor
public class UserActiveAndPasswordChangedFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
                filterChain.doFilter(request, response);
                return;
            }
            UserSecurity userSecurity = (UserSecurity) auth.getPrincipal();

            User user = userSecurity.getUser();
            if (user == null)
                throw new RuntimeException("something wrong in singed User not found");
            if (!user.isActive())
                throw new AccessDeniedException("Account with " + user.getEmail()
                        + " is blocked, please contact Administrator");
            if(!user.isPasswordChanged() && !path.equals("/api/v1/auth/change-password"))
                throw new CredentialExpiredException("change your password to access other resources");
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
            return ;
        }
        filterChain.doFilter(request, response);
    }
}
