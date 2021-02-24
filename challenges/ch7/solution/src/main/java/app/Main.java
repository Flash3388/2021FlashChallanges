package app;

import pdf.Pdf;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        File pdfFile = new File("/home/tomtzook/Documents/Books/war-and-peace.pdf");
        int[][] encoded = new int[][] {
                {2,6}, {14,175}, {8,142}, {4,21}, {49,266}, {2,6},
                {663,269}, {4,2}, {4,50}, {65,166}, {5,108},
                {5,89}, {5,227}, {2,6}, {663,269}, {2,21}, {21,58}
        };
        try (Pdf pdf = new Pdf(pdfFile)) {
            Cipher cipher = new Cipher(pdf);
            System.out.println(cipher.decode(encoded));
        }
    }
}
