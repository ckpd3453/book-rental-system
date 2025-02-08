package com.crio.bookrentalsystem.appConfig;

import com.crio.bookrentalsystem.model.User;
import com.crio.bookrentalsystem.repository.UserRepo;
import com.crio.bookrentalsystem.utility.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtUtil;
    private final UserRepo userRepo;

    public JwtAuthFilter(JwtService jwtUtil, UserRepo userRepo) {
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        if (jwtUtil.validateToken(token)) {
            String email = jwtUtil.getEmailFromToken(token);

            // Load user from DB instead of using UserDetailsService
            User user = userRepo.findByEmail(email);
            if (user == null) {
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole().replace("ROLE_", ""))  // Remove "ROLE_" prefix if present
                    .build();

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
            );
        }
        filterChain.doFilter(request, response);
    }
}
