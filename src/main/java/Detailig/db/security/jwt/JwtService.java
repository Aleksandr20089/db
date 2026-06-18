package Detailig.db.security.jwt;

import Detailig.db.entiti.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtService {
    private static final Duration ACCESS_TOKEN_DURATION = Duration.ofMinutes(15);
    private static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(30);

    private static final String CLAIM_USER_ID =  "userId";
    private static final String CLAIM_ROLE = "role";

    @Value("${jwt.secret}")
    private String jwtSecret;

    public JwtAuthenticationDto generateAuthToken(TockenData tockenData) {
        JwtAuthenticationDto jwtAuthenticationDto = new JwtAuthenticationDto();
        jwtAuthenticationDto.setToken(buildAccessToken(tockenData));
        jwtAuthenticationDto.setRefreshToken(bildRefreshTocken(tockenData.id()));
        return jwtAuthenticationDto;
    }
    public JwtAuthenticationDto refreshBaseTocken(TockenData tockenData, String refreshToken) {
        JwtAuthenticationDto jwtAuthenticationDto = new JwtAuthenticationDto();
        jwtAuthenticationDto.setToken(buildAccessToken(tockenData));
        jwtAuthenticationDto.setRefreshToken(refreshToken);
        return jwtAuthenticationDto;
    }
    private String buildAccessToken(TockenData tockenData) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(String.valueOf(tockenData.id()))
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(ACCESS_TOKEN_DURATION)))
                .claim(CLAIM_ROLE, tockenData.role().stream()
                        .map(Role::name).collect(Collectors.toUnmodifiableList()))
                .signWith(signaKey())
                .compact();
    }
    private String bildRefreshTocken(Long id) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(String.valueOf(id))
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(REFRESH_TOKEN_DURATION)))
                .signWith(signaKey())
                .compact();
    }
    private Claims parsClaims(String token) {
        return Jwts.parser()
                .verifyWith(signaKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public TockenData extratTockendata(String token) {
        Claims claims = parsClaims(token);
        Long userId = Long.parseLong(claims.getSubject());
        Set<Role> role = ExtractRole(claims);
        return new TockenData(userId, role);
    }
    private Set<Role> ExtractRole(Claims claims) {
        List<String> role = claims.get(CLAIM_ROLE, List.class);
        if(role == null || role.isEmpty()) {
            throw   new RuntimeException("Роль зарегистророванного пользователя не может быть пустой ");
        }
        return role.stream()
                .map(Role :: valueOf)
                .collect(Collectors.toUnmodifiableSet());
    }
    public boolean validateToken(String token) {
        try {
            parsClaims(token);
            return true;
        }catch (ExpiredJwtException e) {
            log.warn(" Jwt истек:{} ", e.getMessage() );
        }
        return false;
    }
    private SecretKey signaKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
