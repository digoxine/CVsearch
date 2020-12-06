package com.sorbonne.cvsearches.hooks;

import com.sorbonne.cvsearches.models.Skill;
import com.sorbonne.cvsearches.remote.SkillsApi;
import com.sorbonne.cvsearches.remote.responses.AccessTokenResponse;
import com.sorbonne.cvsearches.remote.responses.ResponseSkill;
import com.sorbonne.cvsearches.services.SkillsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
public class SkillsFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkillsFetcher.class);

    @Autowired
    private SkillsService skillsService;

    @Autowired
    private SkillsApi skillsApi;

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000 , initialDelay = 1000)
    public void fetchAllSkills() {
        LOGGER.info("start fetching skills again...");
        AccessTokenResponse accessTokenResponse = skillsApi.getAccessToken();
        LOGGER.info("AccessToken generated");
        ResponseSkill skills = skillsApi.getAllSkills(
                accessTokenResponse.getToken_type(),
                accessTokenResponse.getAccessToken()
        );
        LOGGER.info("skills fetched");
        List<Skill> skillsPO = skills.getData().stream().parallel()
                .map(sk -> sk.get("name").getAsString())
                .map(sk -> sk.replaceAll("\\(.*\\)", ""))
                .map(String::trim)
                .map(String::toLowerCase)
                .map(Skill::new)
                .collect(Collectors.toList());

        LOGGER.info("Skills parsed");
        skillsService.addAll(skillsPO);
        LOGGER.info("Skills ADDED");
    }
}
