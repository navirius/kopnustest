package com.test.kopnus.utils;

import com.google.gson.Gson;
import com.test.kopnus.model.entity.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.HashMap;
import java.util.Optional;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

import io.jsonwebtoken.*;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public static final String DEFAULT_TOKEN_PREFIX = "Bearer ";

    public static String extractAuthToken(String authHeader, String prefix){
        String token = Optional.ofNullable(authHeader).orElse("");
        if (prefix != null && token.startsWith(prefix)){
            return token.replaceFirst(prefix, "");
        }
        return token;
    }

    public static String getValueFromToken(String token, String attrName){
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(attrName)) return "";

        JSONParser jsonParser = new JSONParser();
        String[] chunks = token.split("\\.");
        String payload = chunks.length > 1 ? chunks[1]:chunks[0];
        try {
            JSONObject obj = (JSONObject) jsonParser.parse(new String(Base64.getDecoder().decode(payload)));
            return (String)obj.getOrDefault(attrName, "");
        } catch (ParseException e) {
            logger.error("Failed to extract value from token!", e);
        }
        return "";
    }


    // The secret key. This should be in a property file NOT under source
    // control and not hard coded in real life. We're putting it here for
    // simplicity.
    private static String SECRET_KEY = "oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";

    public static final String DEFAULT_USERNAME_KEY = "username";
    //Sample method to construct a JWT
    public static String createJWT(String id, String issuer, String subject, long ttlMillis, UserEntity userEntity) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //long nowMillis = System.currentTimeMillis();
        Date now = new Date(ttlMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        String jsonUserEntity = new Gson().toJson(userEntity);
        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .claim(DEFAULT_USERNAME_KEY, jsonUserEntity)
                //.setClaims(new HashMap<String, Object>(){{ put("DEFAULT_USERNAME_KEY", jsonUserEntity); }})
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            //long expMillis = nowMillis + ttlMillis;
            Date exp = DateUtils.addDays(new Date(ttlMillis), 2);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static Claims decodeJWT(String jwt) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
}
