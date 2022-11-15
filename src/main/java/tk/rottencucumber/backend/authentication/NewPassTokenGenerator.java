package tk.rottencucumber.backend.authentication;

import net.bytebuddy.utility.RandomString;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class NewPassTokenGenerator {

    private static SecretKeySpec setKey(final String pass) {
        try {
            byte[] key = pass.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            return new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encrypt(String seed) {
        String secret = RandomString.make(64);
        try {
            SecretKeySpec secretKey = setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            String token = Base64.getEncoder().encodeToString(cipher.doFinal(seed.getBytes(StandardCharsets.UTF_8)));
            System.out.println(secret);
            System.out.println(token);
            return token.substring(0, 64).replaceAll("/", "23zSd") + secret;
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.getMessage());
        }
        return null;
    }

    public static String decrypt(String token) {
        try {
            String cleanToken = token.replaceAll("23zSd", "/");
            String head = cleanToken.substring(0, 64);
            System.out.println(head);
            String tail = cleanToken.substring(64, 128);
            SecretKeySpec secretKey = setKey(tail);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder()
                    .decode(head)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.getMessage());
        }
        return null;
    }
}

