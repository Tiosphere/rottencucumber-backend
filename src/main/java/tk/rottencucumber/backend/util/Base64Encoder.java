package tk.rottencucumber.backend.util;

import org.bouncycastle.util.encoders.Base64;

public class Base64Encoder {

    public static String encode(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return Base64.toBase64String(bytes);
    }
}
