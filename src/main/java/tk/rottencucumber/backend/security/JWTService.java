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

    public String generateToken(String subject) {
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .subject(subject)
                .claim("scope", "USER")
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    public String generatePassToken(String subject) {
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(subject)
                .claim("scope", "PASS")
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    public String getSubject(String token) {
        return decoder.decode(token).getSubject();
    }

    public boolean valid(String token) {
        Instant expire = decoder.decode(token).getExpiresAt();
        return expire != null && expire.isAfter(Instant.now());
    }
}
