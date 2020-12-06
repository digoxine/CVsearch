package com.sorbonne.cvsearches.resources;

import com.sorbonne.cvsearches.models.EsResume;
import com.sorbonne.cvsearches.services.PdfParserService;
import com.sorbonne.cvsearches.services.ResumeService;
import com.sorbonne.cvsearches.services.SkillsService;
import com.sorbonne.cvsearches.utils.StringManipulations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/api/resumes")
public class ResumeResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResumeResource.class);

    @Autowired
    PdfParserService pdfParserService;

    @Autowired
    ResumeService resumeService;

    @Autowired
    SkillsService skillsService;

    @PostMapping()
    public ResponseEntity<EsResume> postResumeFromFile(
            @RequestParam(name = "resume") MultipartFile file
    ) {
        String content = "null";
        EsResume parsedResume;
        if (StringManipulations.isPdf(file.getOriginalFilename())) {
            try {
                content = pdfParserService.getTextFromPDF(file.getInputStream());
                List<String> skills = skillsService.getAllSkills();
                parsedResume = pdfParserService.parseText(content, skills);
                boolean ok = resumeService.insertResumeES(parsedResume, file.getOriginalFilename());
                if (ok) {
                    return ResponseEntity.created(URI.create("/v1/api/resumes")).body(parsedResume);
                } else {
                    return ResponseEntity.noContent().build();
                }
            } catch (IOException e) {
                content = "Error in controller : " + e.getMessage();
                LOGGER.error(content);
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping()
    @ResponseBody
    public ResponseEntity<List<EsResume>> getMatchingResumes(
            @RequestBody MySearchRequest search
    ) throws IOException {
        return ResponseEntity.ok(resumeService.searchResume(search));

    }
}
