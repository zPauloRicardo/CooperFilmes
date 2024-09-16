package me.paulojr.cooperfilme.infra.config.auth;

import me.paulojr.cooperfilme.infra.contexts.user.adapters.UserAuthAdapter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            @Nullable HttpServletRequest request,
            @Nullable HttpServletResponse response,
            @Nullable FilterChain filterChain
    ) throws ServletException, IOException {
        assert request != null;
        final String authHeader = request.getHeader("Authorization");
        if ((authHeader == null || (!authHeader.startsWith("Bearer ")) && !authHeader.startsWith("bearer ")) && filterChain != null) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            assert authHeader != null;
            final String jwt = authHeader.substring(7);

            final String username = this.jwtService.extractUuid(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                final UserAuthAdapter userDetails = this.customUserDetailsService.getByUUID(username);
                if (this.jwtService.isTokenValid(jwt, userDetails)) {
                    final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }


        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
        }
        assert filterChain != null;
        filterChain.doFilter(request, response);
    }
}
