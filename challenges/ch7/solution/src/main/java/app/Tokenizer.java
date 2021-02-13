package app;

import java.util.Iterator;
import java.util.StringTokenizer;

public class Tokenizer {

    public Iterator<String> tokenize(String data) {
        return new Iterator<String>() {
            final StringTokenizer mTokenizer = new StringTokenizer(data);

            @Override
            public boolean hasNext() {
                return mTokenizer.hasMoreTokens();
            }

            @Override
            public String next() {
                String token = mTokenizer.nextToken();
                return stripNonLetters(token);
            }
        };
    }

    private static String stripNonLetters(String token) {
        return token.replaceAll("[^a-zA-Z0-9]", "");
    }
}
