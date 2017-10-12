package com.fangcloud.sdk.auth;

import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.YfyRequestUtil;
import com.fangcloud.sdk.exception.YfyException;
import com.fangcloud.sdk.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.apache.commons.codec.binary.Base64;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.CharBuffer;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Does the "jwt" flow.
 */
public class YfyEnterpriseAuth {
    private static final SecureRandom RAND = new SecureRandom();

    private final YfyRequestConfig requestConfig;
    private Map<String, Object> headers;
    private Key key;

    /**
     * Creates a new instance that will perform the OAuth2 jwt flow using the given OAuth
     * request configuration.
     *
     * @param requestConfig HTTP request configuration, never {@code null}.
     */
    public YfyEnterpriseAuth(YfyRequestConfig requestConfig, final String kid, Key key) {
        if (requestConfig == null) throw new NullPointerException("requestConfig");
        if (kid == null) throw new NullPointerException("kid");

        this.requestConfig = requestConfig;
        this.headers = new HashMap<String, Object>() {{
            put("alg", "RS256");
            put("typ", "JWT");
            put("kid", kid);
        }};
        this.key = key;
    }

    /**
     * Get the enterprise token witch can used to invoke admin api,such as managing departments and groups
     *
     * @param enterpriseId Your enterprise id
     * @return Detailed user access information
     * @throws YfyException
     */
    public YfyAuthFinish getEnterpriseToken(long enterpriseId) throws YfyException {
        Claims claims = new DefaultClaims();
        claims.put("yifangyun_sub_type", "enterprise");
        claims.setSubject(String.valueOf(enterpriseId));
        claims.setExpiration(getExpirationTimeMinutesInTheFuture(1));
        claims.setId(getGeneratedJwtId(16));
        final String compactJws = Jwts.builder().setHeader(headers).setClaims(claims).signWith(SignatureAlgorithm.RS256, key).compact();

        return YfyRequestUtil.doPostInAuth(
                requestConfig,
                YfyAppInfo.getHost().getAuth(),
                "oauth/token",
                new HashMap<String, String>() {{
                    put("grant_type", "jwt");
                    put("assertion", compactJws);
                }},
                YfyAuthFinish.class);
    }

    /**
     * Get the user token witch can used to invoke personal api,such as get folder information
     *
     * @param userId The user you want to operate with
     * @return Detailed user access information
     * @throws YfyException
     */
    public YfyAuthFinish getUserToken(long userId) throws YfyException {
        Claims claims = new DefaultClaims();
        claims.put("yifangyun_sub_type", "user");
        claims.setSubject(String.valueOf(userId));
        claims.setExpiration(getExpirationTimeMinutesInTheFuture(1));
        claims.setId(getGeneratedJwtId(16));
        final String compactJws = Jwts.builder().setHeader(headers).setClaims(claims).signWith(SignatureAlgorithm.RS256, key).compact();

        return YfyRequestUtil.doPostInAuth(
                requestConfig,
                YfyAppInfo.getHost().getAuth(),
                "oauth/token",
                new HashMap<String, String>() {{
                    put("grant_type", "jwt");
                    put("assertion", compactJws);
                }},
                YfyAuthFinish.class);
    }

    private Date getExpirationTimeMinutesInTheFuture(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    private String getGeneratedJwtId(int numberOfBytes) {
        byte[] csrf = new byte[numberOfBytes];
        RAND.nextBytes(csrf);
        return StringUtil.urlSafeBase64Encode(csrf);
    }

    /**
     * Load private pkcs8 pem file to get the private key
     *
     * @param fileStream InputStream of the Pkcs8 file
     * @return Key to construct YfyEnterpriseAuth and used in signature
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(InputStream fileStream) throws Exception {
        String privateKeyPEM;
        Reader reader = new InputStreamReader(fileStream);
        try {
            StringBuilder stringBuilder = new StringBuilder();

            CharBuffer buffer = CharBuffer.allocate(2048);
            while (reader.read(buffer) != -1) {
                buffer.flip();
                stringBuilder.append(buffer);
                buffer.clear();
            }
            privateKeyPEM = stringBuilder.toString();
        } finally {
            reader.close();
        }

        // strip of header, footer, newlines, whitespaces
        privateKeyPEM = privateKeyPEM
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        // decode to get the binary DER representation
        byte[] privateKeyDER = Base64.decodeBase64(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyDER));
        return privateKey;
    }
}
