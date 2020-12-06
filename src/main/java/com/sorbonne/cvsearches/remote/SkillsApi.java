package com.sorbonne.cvsearches.remote;

import com.google.gson.Gson;
import com.sorbonne.cvsearches.remote.responses.AccessTokenResponse;
import com.sorbonne.cvsearches.remote.responses.ResponseSkill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class SkillsApi {

    @Value("${skills.api.client_id}")
    private String CLIENT_ID;
    @Value("${skills.api.client_secret}")
    private String CLIENT_SECRET;
    @Value("${skills.api.scope}")
    private String SCOPE;
    @Value("${skills.api.grant_type}")
    private String GRANT_TYPE;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;



    public AccessTokenResponse getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", CLIENT_ID);
        map.add("client_secret", CLIENT_SECRET);
        map.add("scope", SCOPE);
        map.add("grant_type", GRANT_TYPE);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> response =
                restTemplate.exchange("https://auth.emsicloud.com/connect/token",
                        HttpMethod.POST,
                        entity,
                        String.class);
        return gson.fromJson(response.getBody(), AccessTokenResponse.class);
    }

    public ResponseSkill getAllSkills(String tokenType, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", tokenType + " " + accessToken);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(
                "https://emsiservices.com/skills/versions/latest/skills",
                HttpMethod.GET,
                entity,
                String.class
        );

        return gson.fromJson(response.getBody(), ResponseSkill.class);
    }
}
