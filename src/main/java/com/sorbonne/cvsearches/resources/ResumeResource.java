package com.sorbonne.cvsearches.resources;

import com.sorbonne.cvsearches.models.EsResume;
import com.sorbonne.cvsearches.services.PdfParserService;
import com.sorbonne.cvsearches.utils.StringManipulations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/v1/api/resumes")
public class ResumeResource {

    @Autowired
    PdfParserService pdfParserService;

    @PostMapping()
    public ResponseEntity<EsResume> postResumeFromFile(
            @RequestParam(name = "resume") MultipartFile file
    ) {
        String content = "null";
        if (StringManipulations.isPdf(file.getOriginalFilename())) {
            try {
                content = pdfParserService.getTextFromPDF(file.getInputStream());
            } catch (IOException e) {
                content = "Error in controller : " + e.getMessage();
            }
        }
        return ResponseEntity.created(URI.create("/v1/api/resumes")).body(new EsResume(1L, content));
    }
}
