package com.parrot.backend.services.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService implements IJwtService {
  private final long EXPIRATION_TIME = 7200000;
  private final String KEY = "244226452948404D635166546A576E5A7234753778217A25432A462D4A614E64";

  public String generateToken(UUID userId) {
    return Jwts
        .builder()
        .setSubject(userId.toString())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(genSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isValidToken(String token, String userId) {
    var tokenExpiration = getClaim(token, Claims::getExpiration);

    return !tokenExpiration.before(new Date());
  }

  public String getTokenSubject(String token) {
    return getClaim(token, Claims::getSubject);
  }

  private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
    var claims = Jwts
        .parserBuilder()
        .setSigningKey(genSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();

    return claimsResolver.apply(claims);
  }

  private Key genSignInKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEY));
  }
}
