package com.sorbonne.cvsearches.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class SkillsService {


    public List<String> getAllSkills() {
        return Arrays.asList(
            "java",
            "rust",
            "haskell",
            "python",
            "javascript",
            "js",
            "typescript",
            "angular",
            "react",
            "nodejs",
            "node",
            "go",
            "spring",
            "keras"
        );
    }

}
