package vn.edu.hcmuaf.fit.fahabook.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.core.io.Resource;

import lombok.SneakyThrows;

public class KeyUtil {
    private KeyUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    @SneakyThrows
    public static RSAPrivateKey parsePrivateKey(Resource resource) {
        String key = readPem(resource.getInputStream(), "PRIVATE KEY");
        byte[] decoded = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) factory.generatePrivate(spec);
    }

    @SneakyThrows
    public static RSAPublicKey parsePublicKey(Resource resource) {
        String key = readPem(resource.getInputStream(), "PUBLIC KEY");
        byte[] decoded = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) factory.generatePublic(spec);
    }

    private static String readPem(InputStream inputStream, String type) throws IOException {
        String pem = new String(inputStream.readAllBytes());
        pem = pem.replace("-----BEGIN " + type + "-----", "")
                .replace("-----END " + type + "-----", "")
                .replaceAll("\\s", "");
        return pem;
    }
}
