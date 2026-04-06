package com.gymregistration.GymRegistration.config;

import com.gymregistration.GymRegistration.service.JwtService;
import com.gymregistration.GymRegistration.repository.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilterConfig extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserRepository userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Busca el header Authorization
        String authHeader = request.getHeader("Authorization");

        // 2. Si no tiene token, sigue sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extrae el token sacando el "Bearer " del principio
        String token = authHeader.substring(7);

        // 4. Extrae el email y el rol del token
        String email = jwtService.extractEmail(token);
        String role = jwtService.extractRole(token);

        // 5. Si el email es válido y no hay autenticación previa
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 6. Valida el token
            if (jwtService.isTokenValid(token, email)) {

                // 7. Crea la autenticación con el rol
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );

                // 8. Le dice a Spring que este usuario está autenticado
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}