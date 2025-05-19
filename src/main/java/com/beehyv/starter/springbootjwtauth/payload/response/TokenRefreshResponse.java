package com.beehyv.starter.springbootjwtauth.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefreshResponse {
    private String accessToken;
    private String refreshToken;
    @Value("${jwt.token.prefix}")
    private String tokenType = "Bearer";

    public TokenRefreshResponse(String accessToken, String refreshToken){
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }

}
