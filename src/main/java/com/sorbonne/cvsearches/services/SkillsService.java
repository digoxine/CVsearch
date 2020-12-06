package com.sorbonne.cvsearches.services;

import com.sorbonne.cvsearches.models.Skill;
import com.sorbonne.cvsearches.repositories.SkillRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class SkillsService {

    @Autowired
    private SkillRepository skillRepository;

    public List<String> getAllSkills() {
        return StreamSupport.stream(skillRepository.findAll().spliterator(), true)
                .map(Skill::getName)
                .collect(Collectors.toList());
    }

    public void addAll(List<Skill> skillsPO) {
        if (skillRepository.count() != 0) {
            skillRepository.deleteAll(); // empty the table
        }
        skillRepository.saveAll(skillsPO);
    }
}
