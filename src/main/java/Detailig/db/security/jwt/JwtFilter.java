package Detailig.db.security.jwt;

import Detailig.db.security.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String tocken = getTokenFromRequest(request);
        if (tocken != null && jwtService.validateToken(tocken)) {
            log.info("Токен получен: {}", tocken);
            TockenData tockenData = jwtService.extratTockendata(tocken);
            List<GrantedAuthority> authorities = tockenData.role().stream().map(role -> new SimpleGrantedAuthority(role.name()))
                    .collect(Collectors.toUnmodifiableList());
            UserDetails userDetails = new CustomUserDetails(
                    String.valueOf(tockenData.id()),
                    "",
                    authorities
            );
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Для пользователя с таким айди {} установлена аутификация: ", tockenData.id());
        }
        filterChain.doFilter(request,response);

    }
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerTocken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerTocken != null && bearerTocken.startsWith("Bearer ")) {
            return bearerTocken.substring(7);
        }
        return null;
    }
}