package org.upgrad.paymentservice.model.JWT;

public class JwtTokenResponse {
    private String accessToken;

    public JwtTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
