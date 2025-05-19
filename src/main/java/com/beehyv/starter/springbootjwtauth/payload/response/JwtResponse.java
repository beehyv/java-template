package com.beehyv.starter.springbootjwtauth.payload.response;

import com.beehyv.starter.springbootjwtauth.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    @Value("${jwt.token.prefix}")
    private String type = "Bearer";
    private String refreshToken;
    private Long id;
    private String username;
    private String email;
    private List<ERole> roles;

    public JwtResponse(String accessToken, String refreshToken, Long id, String username, String email, List<ERole> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
