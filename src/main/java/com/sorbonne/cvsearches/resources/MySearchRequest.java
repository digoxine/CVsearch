package com.sorbonne.cvsearches.resources;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

public class MySearchRequest {
    List<String> skills;
    int minimumMatches;

    public MySearchRequest(List<String> skills) {
        this.skills = skills;
        minimumMatches = 3;
    }

    public int getMinimumMatches() {
        return minimumMatches;
    }

    public List<String> getSkills() {
        return skills;
    }
}
