package com.restateai.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.restateai.model.User;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Log4j2
public class FirebaseSecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        verifyToken(httpServletRequest);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void verifyToken(HttpServletRequest request) {
        FirebaseToken decodedToken = null;
        String token = getBearerToken(request);
        try {

            if (token != null && !token.equalsIgnoreCase("undefined")) {
                decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            }

        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            log.error("Firebase Exception:: " + e.getLocalizedMessage());
        }
        User user = firebaseTokenToUserDto(decodedToken);
        if (user != null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
                    new Credentials(decodedToken, token), null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private String getBearerToken(HttpServletRequest request) {
        String bearerToken = null;
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            bearerToken = authorization.substring(7);
        }
        return bearerToken;
    }

    private User firebaseTokenToUserDto(FirebaseToken decodedToken) {
        User user = null;
        if (decodedToken != null) {
            user = new User();
            user.setUid(decodedToken.getUid());
            user.setName(decodedToken.getName());
            user.setEmail(decodedToken.getEmail());
            user.setEmailVerified(decodedToken.isEmailVerified());
        }
        return user;
    }
}
