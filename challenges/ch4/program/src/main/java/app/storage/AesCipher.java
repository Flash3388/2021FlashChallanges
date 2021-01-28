package app.storage;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class AesCipher {

    private final Cipher mCipher;
    private final SecretKeySpec mKey;

    public AesCipher(String key) throws CryptoException {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");

            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            keyBytes = sha.digest(keyBytes);
            keyBytes = Arrays.copyOf(keyBytes, 16);
            mKey = new SecretKeySpec(keyBytes, "AES");

            mCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new CryptoException(e);
        }
    }

    public String encrypt(String data) throws CryptoException {
        try {
            mCipher.init(Cipher.ENCRYPT_MODE, mKey);

            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            dataBytes = mCipher.doFinal(dataBytes);

            return Base64.getEncoder().encodeToString(dataBytes);
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new CryptoException(e);
        }
    }

    public String decrypt(String data) throws CryptoException {
        try {
            mCipher.init(Cipher.DECRYPT_MODE, mKey);

            byte[] dataBytes = Base64.getDecoder().decode(data);
            dataBytes = mCipher.doFinal(dataBytes);

            return new String(dataBytes, StandardCharsets.UTF_8);
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new CryptoException(e);
        }
    }
}
