package Detailig.db.security.jwt;

import lombok.Data;

@Data
public class JwtAuthenticationDto {
    public String token;
    public String refreshToken;
}
