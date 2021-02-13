package app;

import pdf.Pdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cipher {

    private final WordFinder mWordFinder;
    private final Tokenizer mTokenizer;

    public Cipher(Pdf file) {
        mWordFinder = new WordFinder(file);
        mTokenizer = new Tokenizer();
    }

    public int[][] encode(String data) throws IOException {
        List<WordLocation> wordLocations = new ArrayList<>();

        Iterator<String> tokenizer = mTokenizer.tokenize(data);
        while (tokenizer.hasNext()) {
            String token = tokenizer.next();
            WordLocation wordLocation = mWordFinder.find(token);
            wordLocations.add(wordLocation);
        }

        int[][] encoded = new int[wordLocations.size()][2];
        for (int i = 0; i < wordLocations.size(); i++) {
            WordLocation wordLocation = wordLocations.get(i);
            encoded[i][0] = wordLocation.getPage();
            encoded[i][1] = wordLocation.getIndex();
        }

        return encoded;
    }

    public String decode(int[][] encoded) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (int[] word : encoded) {
            String token = mWordFinder.wordAt(word[0], word[1]);
            builder.append(token);
            builder.append(' ');
        }

        return builder.toString();
    }
}
