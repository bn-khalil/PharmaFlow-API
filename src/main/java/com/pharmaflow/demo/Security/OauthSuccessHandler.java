package com.pharmaflow.demo.Security;

import com.pharmaflow.demo.Services.Impl.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OauthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserSecurity userSecurity = (UserSecurity) authentication.getPrincipal();
        String token = jwtService.generateToken(userSecurity);
        String targetUrl = "http://localhost:9090/api/v1/auth/call-back?token=" + token;
        System.out.println("---------------------->  " + token);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
