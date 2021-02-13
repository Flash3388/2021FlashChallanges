package app;

import pdf.Pdf;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        File pdfFile = new File("/home/tomtzook/Documents/Books/war-and-peace.pdf");
        String text = "sometime";
        try (Pdf pdf = new Pdf(pdfFile)) {
            Cipher cipher = new Cipher(pdf);
            int[][] en = cipher.encode(text);
            System.out.println(cipher.decode(en));
        }
    }
}
