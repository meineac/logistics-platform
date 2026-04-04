package utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class KeyManager {
    private static final byte[] SALT = "LogisticsApp".getBytes();

    public static SecretKey deriveKeyFromPassword(String password) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), SALT, 65536, 128);
            SecretKey tmp = factory.generateSecret(spec);

            return new SecretKeySpec(tmp.getEncoded(), "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algorithm 'PBKDF2WithHmacSHA256' not found.", e);
        } catch (InvalidKeySpecException e) {
            throw new IllegalStateException("The given key specification is inappropriate for this secret key factory to produce a secret key.", e);
        }
    }
}