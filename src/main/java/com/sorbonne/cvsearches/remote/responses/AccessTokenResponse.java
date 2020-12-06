package com.sorbonne.cvsearches.remote.responses;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccessTokenResponse {
    private String access_token;
    private int expires_in;
    String token_type;

    public String getAccessToken() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }
}
