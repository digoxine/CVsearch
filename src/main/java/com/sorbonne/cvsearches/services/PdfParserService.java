package com.sorbonne.cvsearches.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.PDFBox;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class PdfParserService {


    private static final Logger LOGGER = LoggerFactory.getLogger(PdfParserService.class);

    public String getTextFromPDF(InputStream stream) {
        PDFParser parser = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        PDFTextStripper pdfStripper;
        String parsedText;

        try {
            parser = new PDFParser(stream);
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
            return parsedText.replaceAll("[^A-Za-z0-9. ]+", "");
        } catch (IOException e) {
            LOGGER.debug(e.getMessage());
            try {
                if (cosDoc != null)
                    cosDoc.close();
                if (pdDoc != null)
                    pdDoc.close();
            } catch (Exception e1) {
                LOGGER.debug(e1.getMessage());
            }
            return "";
        }
    }

    public String reformatText(String content) {
        // passer tout en minuscule
        // enlever les accents
        // remplacer les \r\n par des espaces
        // isoler les hashtag
        // isoler les liens http(s)?://[A-Za-z~0-9-./]*
        // isoler les adresses mails  [A-Za-z0-9_-]*@[A-Za-z0-9_-]*.[A-Za-z0-9_-]*
        // isoler le numéro de téléphone  0[0-9]{9} | +33
        // enlever les caractères spéciaux [^A-Za-z0-9.~+#@"'() /;,:-]+
        // lister les compétences selon le dico
        return "";
    }
}
