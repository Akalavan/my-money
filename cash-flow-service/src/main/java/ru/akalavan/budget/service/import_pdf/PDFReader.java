package ru.akalavan.budget.service.import_pdf;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;
import ru.akalavan.budget.entity.MonetaryTransaction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

abstract public class PDFReader {
    public List<MonetaryTransaction> readPdf(String path) {
        List<MonetaryTransaction> monetaryTransactions = new ArrayList<>();
        try (PDDocument document = openDocument(path)) {
            AccessPermission ap = document.getCurrentAccessPermission();

            if (!ap.canExtractContent()) {
                throw new IOException("You do not have permission to extract text");
            }

            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);

            monetaryTransactions = readPages(document, stripper);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return monetaryTransactions;
    }

    private List<MonetaryTransaction> readPages(PDDocument document, PDFTextStripper stripper) throws IOException {
        List<MonetaryTransaction> monetaryTransactionsList = new ArrayList<>();
        for (int page = 1; page <= document.getNumberOfPages() - 1; page++) {
            stripper.setStartPage(page);
            stripper.setEndPage(page);
            stripper.setWordSeparator("|");

            // let the magic happen
            readMonetaryTransaction(stripper.getText(document), page, monetaryTransactionsList);
        }

        return monetaryTransactionsList;
    }

    private PDDocument openDocument(String path) throws IOException {
        return Loader.loadPDF(new File(path));
    }

    abstract void readMonetaryTransaction(String text, int page, List<MonetaryTransaction> monetaryTransactionList);
}
