package com.sorbonne.cvsearches.remote.responses;

import com.google.gson.JsonObject;

import java.util.List;

public class ResponseSkill {
    List<JsonObject> attributions;
    List<JsonObject> data;

    public List<JsonObject> getData() {
        return data;
    }
}
