package uz.pdp.authservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.authservice.entity.Role;


import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {
    private static final long expirationTime = 1000 * 60 * 60 * 24;
    private static final String key = "secretKey";

    public String generateToken(String username, Set<Role> roleSet) {
        Date expireDate = new Date(System.currentTimeMillis() + expirationTime);
        String token = Jwts.
                builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("roles", roleSet)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        return token;
    }

    public String getEmailFromToken(String token){
        try {
            String email = Jwts
                    .parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return email;
        }catch (Exception e){
            return null;
        }
    }

}
