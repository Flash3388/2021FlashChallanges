import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Cypher {
    private static final int LETTERS_IN_ALPHABET = 26;

    private final int shiftValue;

    public Cypher(int shiftValue) {
        this.shiftValue = shiftValue%LETTERS_IN_ALPHABET;
    }

    public String encrypt(Path path) throws IOException {
        return encrypt(Files.readString(path));
    }

    public String encrypt(String str) {
        String encrypted = "";

        for(char c: str.toCharArray())
            if(Character.isAlphabetic(c))
                encrypted += correctEncryptionOverflow((char) (c + shiftValue), c);
            else
                encrypted += c;

        return encrypted;
    }

    public String decrypt(Path path) throws IOException {
        return decrypt(Files.readString(path));
    }

    public String decrypt(String encrypted) {
        String decrypted = "";

        for(char c: encrypted.toCharArray())
            if(Character.isAlphabetic(c))
                decrypted += correctDecryptionOverflow((char) (c - shiftValue), c);
            else
                decrypted += c;

        return decrypted;
    }

    private char correctDecryptionOverflow(char decryptedChar, char initialLetter) {
        if(Character.isLowerCase(initialLetter) && decryptedChar < 'a')
            return (char) (decryptedChar + LETTERS_IN_ALPHABET);

        else if(Character.isUpperCase(initialLetter) && decryptedChar < 'A')
            return (char) (decryptedChar + LETTERS_IN_ALPHABET);

        return decryptedChar;
    }

    private char correctEncryptionOverflow(char encryptedChar, char initialLetter) {
        if(Character.isLowerCase(initialLetter) && encryptedChar > 'z')
            return (char) (encryptedChar - LETTERS_IN_ALPHABET);

        else if(Character.isUpperCase(initialLetter) && encryptedChar > 'Z')
            return (char) (encryptedChar - LETTERS_IN_ALPHABET);

        return encryptedChar;
    }
}
