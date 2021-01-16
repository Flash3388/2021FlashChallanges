public class Decrypter {
    private static final int LETTERS_IN_ALPHABET = 26;

    private final int shiftValue;

    public Decrypter(int shiftValue) {
        this.shiftValue = shiftValue%LETTERS_IN_ALPHABET;
    }

    public String decrypt(String encrypted) {
        String decrypted = "";

        for(char c: encrypted.toCharArray())
            if(Character.isAlphabetic(c))
                decrypted += correctOverflow((char) (c - shiftValue), c);

        return decrypted;
    }

    private char correctOverflow(char decryptedChar, char initialLetter) {
        if(Character.isLowerCase(initialLetter) && decryptedChar < 'a')
            return (char) (decryptedChar + LETTERS_IN_ALPHABET);

        else if(Character.isUpperCase(initialLetter) && decryptedChar < 'A')
            return (char) (decryptedChar + LETTERS_IN_ALPHABET);

        return decryptedChar;
    }
}
