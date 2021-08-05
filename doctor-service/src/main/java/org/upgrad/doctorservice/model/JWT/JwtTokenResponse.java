package org.upgrad.doctorservice.model.JWT;

public class JwtTokenResponse {
    private String accessToken;

    public JwtTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
