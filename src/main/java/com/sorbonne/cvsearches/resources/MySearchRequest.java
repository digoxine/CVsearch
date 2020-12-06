package com.sorbonne.cvsearches.resources;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class MySearchRequest {
    public List<String> skills;
    int minimumMatches;


    public int getMinimumMatches() {
        return minimumMatches;
    }

    public List<String> getSkills() {
        return skills;
    }
}
