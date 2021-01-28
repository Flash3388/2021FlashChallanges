public class Ceaser {

    public static String CeasarDecipher(String cipher, int shift) {
        String decipher = "";

        for (int i = 0; i < cipher.length(); ++i) {
            char c = cipher.charAt(i);

            if (c >= 'A' && c <= 'Z') {
                c = (char) (((c - 'A' + 26 - shift) % 26) + 'A');
            } else if (c >= 'a' && c <= 'z') {
                c = (char) (((c - 'a' + 26 - shift) % 26) + 'a');
            }

            decipher += c;
        }

        return decipher;
    }
}