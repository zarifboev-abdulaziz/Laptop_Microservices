package uz.pdp.apigateway.security;//package uz.pdp.apigateway.security;
//
//import io.jsonwebtoken.Jwts;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JwtProvider {
//    private static final String key = "secretKey";
//
//    public String getEmailFromToken(String token){
//        try {
//            return Jwts
//                    .parser()
//                    .setSigningKey(key)
//                    .parseClaimsJws(token)
//                    .getBody()
//                    .getSubject();
//        }catch (Exception e){
//            return null;
//        }
//    }
//
//}
