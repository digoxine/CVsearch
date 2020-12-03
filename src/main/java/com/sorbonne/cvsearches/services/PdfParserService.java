package com.sorbonne.cvsearches.services;

import com.sorbonne.cvsearches.models.EsResume;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.PDFBox;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            return parsedText;
//            return parsedText.replaceAll("[^A-Za-z0-9. ]+", "");
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

    public EsResume parseText(String content, List<String> skillsDict) {
        // passer tout en minuscule
        // enlever les accents
        // remplacer les \r\n par des espaces
        String text = content.toLowerCase()
                        .replaceAll("é", "e")
                        .replaceAll("è", "e")
                        .replaceAll("ê", "e")
                        .replaceAll("ç", "c")
                        .replaceAll("à", "a")
                        .replaceAll("ù", "u")
                        .replaceAll("î", "i")
                        .replaceAll("\r\n", " ");

        // isoler les hashtag
        Pattern hashtagsPattern = Pattern.compile("(?:^|\\s|[\\p{Punct}&&[^/]])(#[\\p{L}0-9-_]+)");
        Matcher matcher = hashtagsPattern.matcher(text);
        List<String> hashtags = new ArrayList<>();
        while (matcher.find()) {
            hashtags.add(matcher.group());
        }
        text = text.replaceAll("(?:^|\\s|[\\p{Punct}&&[^/]])(#[\\p{L}0-9-_]+)", " ");


        // isoler les adresses mails  [A-Za-z0-9_-]*@[A-Za-z0-9_-]*.[A-Za-z0-9_-]*
        Pattern mailPattern = Pattern.compile("[A-Za-z0-9_-]*@[A-Za-z0-9_-]*.[A-Za-z0-9_-]*");
        matcher = mailPattern.matcher(text);
        List<String> mails = new ArrayList<>();
        while (matcher.find()) {
                mails.add(matcher.group());
        }
        text = text.replaceAll("[A-Za-z0-9_-]*@[A-Za-z0-9_-]*.[A-Za-z0-9_-]*", " ");

        // isoler les liens http(s)?://[A-Za-z~0-9-./]*
        Pattern linkPattern = Pattern.compile("(https?|ftp|file):\\/\\/[-a-zA-Z0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#\\/%=~_|]");
        matcher = linkPattern.matcher(text);
        List<String> liens = new ArrayList<>();
        while (matcher.find()) {
            liens.add(matcher.group());
        }
        text = text.replaceAll("(https?|ftp|file):\\/\\/[-a-zA-Z0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#\\/%=~_|]", " ");

        // isoler le numéro de téléphone  0[0-9]{9} | +33
        Pattern numerosPattern = Pattern.compile("(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}");
        matcher = numerosPattern.matcher(text);
        List<String> numeros = new ArrayList<>();
        while (matcher.find()) {
            numeros.add(matcher.group());
        }
        text = text.replaceAll("(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}", " ");
        // enlever les caractères spéciaux [^A-Za-z0-9.~+#@"'() /;,:-]+
        text = text.replaceAll("[^A-Za-z0-9.~+#@\"'() /;,:-]+", " ");

        // lister les compétences selon le dico
        List<String> comptences = new ArrayList<>();
        String[] words = text.split("\\s");
        for (String word: words) {
            if (skillsDict.contains(word)) {
                comptences.add(word);
            }
        }

        return new EsResume(comptences, mails, hashtags, numeros, liens);
    }

}
