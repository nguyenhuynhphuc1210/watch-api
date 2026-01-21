package com.example.watch.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

        private final JwtService jwtService;
        private final CustomUserDetailsService userDetailsService;

        public JwtAuthenticationFilter(
                        JwtService jwtService,
                        CustomUserDetailsService userDetailsService) {
                this.jwtService = jwtService;
                this.userDetailsService = userDetailsService;
        }

        @Override
        protected void doFilterInternal(
                        HttpServletRequest request,
                        HttpServletResponse response,
                        FilterChain filterChain) throws ServletException, IOException {

                String path = request.getServletPath();

                if (path.startsWith("/api/auth")) {
                        filterChain.doFilter(request, response);
                        return;
                }

                if (SecurityContextHolder.getContext().getAuthentication() != null) {
                        filterChain.doFilter(request, response);
                        return;
                }

                String authHeader = request.getHeader("Authorization");

                if (authHeader != null && authHeader.startsWith("Bearer ")) {

                        String token = authHeader.substring(7);

                        if (jwtService.isTokenValid(token)) {

                                String email = jwtService.extractEmail(token);

                                CustomUserDetails userDetails = (CustomUserDetails) userDetailsService
                                                .loadUserByUsername(email);

                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                                userDetails, // ✅ PRINCIPAL = UserDetails
                                                null,
                                                userDetails.getAuthorities());

                                authentication.setDetails(
                                                new WebAuthenticationDetailsSource()
                                                                .buildDetails(request));

                                SecurityContextHolder.getContext()
                                                .setAuthentication(authentication);
                        }
                }

                filterChain.doFilter(request, response);
        }

}
