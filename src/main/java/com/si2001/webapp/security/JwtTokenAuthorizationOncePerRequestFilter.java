package com.si2001.webapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenAuthorizationOncePerRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenAuthorizationOncePerRequestFilter.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Estrai il token dall'header Authorization
        final String requestTokenHeader = request.getHeader("Authorization");
        logger.info("Header 'Authorization' ricevuto: {}", requestTokenHeader);

        String username = null;
        String jwtToken = null;

        // Verifica se l'header inizia con 'Bearer '
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            logger.info("JWT Token estratto: {}", jwtToken);

            try {
                // Estrai l'username dal token
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                logger.info("Username estratto dal token: {}", username);

                // Estrai i claims dal token per ottenere il ruolo
                Claims claims = jwtTokenUtil.getAllClaimsFromToken(jwtToken);
                String role = claims.get("ruolo", String.class);
                logger.info("Ruolo estratto dal token JWT: {}", role);

            } catch (IllegalArgumentException e) {
                logger.error("Errore nell'ottenere il JWT Token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("Il JWT Token è scaduto", e);
            }
        } else {
            logger.warn("Il token JWT non inizia con 'Bearer ' o è assente");
        }

        // Se il token è valido e l'utente non è già autenticato
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.info("Validazione del token in corso per l'utente: {}", username);

            // Carica i dettagli dell'utente dal servizio
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

            // Verifica la validità del token
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                // Crea un'autenticazione basata sui dettagli dell'utente
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Imposta i dettagli di autenticazione per la richiesta corrente
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Imposta l'autenticazione nel contesto di sicurezza di Spring
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                // Log delle autorità assegnate all'utente
                logger.info("Autenticazione settata nel SecurityContext con autorità: {}", authenticationToken.getAuthorities());

                // Log delle autorità effettive nel SecurityContext
                logger.info("Autorità effettive dopo l'autenticazione: {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());

                logger.info("Autenticazione avvenuta con successo per l'utente: {}", username);
            } else {
                logger.warn("Il token JWT non è valido per l'utente: {}", username);
            }
        } else if (username == null) {
            logger.warn("L'username non è stato estratto dal token o il token è nullo");
        } else {
            logger.info("Utente già autenticato, skip dell'autenticazione");
        }

        // Continua la catena dei filtri
        chain.doFilter(request, response);
    }
}
