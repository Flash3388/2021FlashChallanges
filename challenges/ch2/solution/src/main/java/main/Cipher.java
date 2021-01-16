package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Cipher {

    private static final int LETTERS_IN_ALPHABET = 26;

    private final int shiftValue;

    public Cipher(int shiftValue) {
        this.shiftValue = shiftValue%LETTERS_IN_ALPHABET;
    }

    public String encrypt(Path path) throws IOException {
        return encrypt(Files.readString(path));
    }

    public String encrypt(String str) {
        StringBuilder encrypted = new StringBuilder();

        for(char c: str.toCharArray())
            if(Character.isAlphabetic(c))
                encrypted.append(correctEncryptionOverflow((char) (c + shiftValue), c));
            else
                encrypted.append(c);

        return encrypted.toString();
    }

    public String decrypt(Path path) throws IOException {
        return decrypt(Files.readString(path));
    }

    public String decrypt(String encrypted) {
        StringBuilder decrypted = new StringBuilder();

        for(char c: encrypted.toCharArray())
            if(Character.isAlphabetic(c))
                decrypted.append(correctDecryptionOverflow((char) (c - shiftValue), c));
            else
                decrypted.append(c);

        return decrypted.toString();
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
