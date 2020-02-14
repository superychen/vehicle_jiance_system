package com.cqyc.login.comm.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-10-27
 */
public class JwtUtils {
    /**
     * 秘钥
     */
    public static final String PRIVATE_KEY = "yc_is_handsome";
    /**
     * token过期时间
     */
    public static final long TOKEN_EXPIRE_TIME = 20 * 60 * 1000;
    /**
     * refreshToken过期时间
     */
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 2400;
    /**
     * 签发人
     */
    private static final String ISSUER = "issuer";

    /**
     * 私钥加密token
     *
     * @return token
     */
    public static String generateToken(String username) {
        Date now = new Date();
        Algorithm algorithm = Algorithm.HMAC256(PRIVATE_KEY);

        String token = JWT.create()
                //签发人
                .withIssuer(ISSUER)
                //签发时间
                .withIssuedAt(now)
                //过期时间
                .withExpiresAt(new Date(now.getTime() + TOKEN_EXPIRE_TIME))
                //保存身份标识
                .withClaim("username", username)
                .sign(algorithm);
        return token;
    }

    /**
     * 验证token
     */
    public static boolean verify(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(PRIVATE_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 从token获取username
     */
    public static String getUsername(String token) {
        try {
            return JWT.decode(token).getClaim("username").asString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
