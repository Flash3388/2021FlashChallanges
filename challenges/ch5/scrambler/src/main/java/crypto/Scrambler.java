package crypto;

import io.Resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Scrambler {

    private final AesCipher mCipher;

    public Scrambler() {
        try {
            String content = Resources.getResourceContent("kps.asd");
            mCipher = new AesCipher(content);
        } catch (CryptoException | IOException e) {
            throw new Error(e);
        }
    }

    public String scramble(String data) {
        try {
            return mCipher.encrypt(data);
        } catch (CryptoException e) {
            throw new Error(e);
        }
    }

    public String unscramble(String data) {
        try {
            return mCipher.decrypt(data);
        } catch (CryptoException e) {
            throw new Error(e);
        }
    }
}
