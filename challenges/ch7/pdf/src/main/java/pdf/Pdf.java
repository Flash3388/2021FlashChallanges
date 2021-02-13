package pdf;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;

public class Pdf implements AutoCloseable {

    private PdfReader mPdfReader;

    public Pdf(PdfReader pdfReader) {
        mPdfReader = pdfReader;
    }

    public Pdf(File file) throws IOException {
        this(new PdfReader(file.getAbsolutePath()));
    }

    public int getPageCount() {
        return mPdfReader.getNumberOfPages();
    }

    public String readPage(int pageNumber) throws IOException {
        return PdfTextExtractor.getTextFromPage(mPdfReader, pageNumber);
    }

    @Override
    public void close() {
        mPdfReader.close();
    }
}
