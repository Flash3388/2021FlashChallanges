package crypto;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Scrambler {

    private final AesCipher mCipher;

    public Scrambler() {
        try {
            Path keyfile = new File(Scrambler.class.getClassLoader()
                    .getResource("kps.asd")
                    .toURI())
                    .toPath();
            String content = new String(Files.readAllBytes(keyfile), StandardCharsets.UTF_8);
            mCipher = new AesCipher(content);
        } catch (CryptoException | IOException | URISyntaxException e) {
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
