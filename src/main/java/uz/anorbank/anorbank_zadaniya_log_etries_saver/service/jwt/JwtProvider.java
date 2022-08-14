package uz.anorbank.anorbank_zadaniya_log_etries_saver.service.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.User;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Util;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private static final long expire = 1000 * 60 * 60 * 12;
    private static final String key = "AqishUsBAsusbaJs)a9s!s_-";
    private final Util util;


    public String generateToken(String username, User user) {

        Claims claims = Jwts.claims().setSubject(username);
        try {
            claims.put("user_roles", new ObjectMapper().writeValueAsString(user.getUserRoleSet()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public String getUsername(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }


}
