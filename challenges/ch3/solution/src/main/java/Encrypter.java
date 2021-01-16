public class Encrypter {
    private static final int LETTERS_IN_ALPHABET = 26;

    private final int shiftValue;

    public Encrypter(int shiftValue) {
        this.shiftValue = shiftValue%LETTERS_IN_ALPHABET;
    }

    public String encrypt(String str) {
        String encrypted = "";

        for(char c: str.toCharArray())
            if(Character.isAlphabetic(c))
                encrypted += correctOverflow((char) (c + shiftValue), c);

        return encrypted;
    }

    private char correctOverflow(char encryptedChar, char initialLetter) {
        if(Character.isLowerCase(initialLetter) && encryptedChar > 'z')
            return (char) (encryptedChar - LETTERS_IN_ALPHABET);

        else if(Character.isUpperCase(initialLetter) && encryptedChar > 'Z')
            return (char) (encryptedChar - LETTERS_IN_ALPHABET);

        return encryptedChar;
    }
}
