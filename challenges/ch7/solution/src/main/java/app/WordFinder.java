package app;

import pdf.Pdf;

import java.io.EOFException;
import java.io.IOException;
import java.util.Iterator;

public class WordFinder {

    private final Pdf mPdf;
    private final Tokenizer mTokenizer;

    public WordFinder(Pdf pdf) {
        mPdf = pdf;
        mTokenizer = new Tokenizer();
    }

    public WordLocation find(String word) throws IOException {
        for (int i = 1; i <= mPdf.getPageCount(); i++) {
            String pageData = mPdf.readPage(i);
            int index = findToken(pageData, word);
            if (index >= 0) {
                return new WordLocation(i, index);
            }
        }

        throw new EOFException();
    }

    public String wordAt(int pageNumber, int index) throws IOException {
        String pageData = mPdf.readPage(pageNumber);
        return tokenAt(pageData, index);
    }

    private int findToken(String data, String token) {
        Iterator<String> tokenizer = mTokenizer.tokenize(data);
        int index = 0;
        while (tokenizer.hasNext()) {
            String foundToken = tokenizer.next();
            if (foundToken.equalsIgnoreCase(token)) {
                return index;
            }

            index++;
        }

        return -1;
    }

    private String tokenAt(String data, int index) throws EOFException {
        Iterator<String> tokenizer = mTokenizer.tokenize(data);
        int i;
        String foundToken = null;
        for (i = 0; i < index && tokenizer.hasNext(); i++) {
             foundToken = tokenizer.next();
        }

        if (i == index) {
            return foundToken;
        }

        throw new EOFException();
    }
}
