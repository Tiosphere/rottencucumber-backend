package tk.rottencucumber.backend.security;

import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JWTService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    public JWTService(JwtEncoder encoder, JwtDecoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    public String generateToken(String username) {
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .subject(username)
                .claim("scope", "USER")
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    public String getUsername(String token) {
        return decoder.decode(token).getSubject();
    }

    public boolean valid(String token) {
        Jwt jwt = decoder.decode(token);
        return jwt.getExpiresAt().isAfter(Instant.now());
    }
}
