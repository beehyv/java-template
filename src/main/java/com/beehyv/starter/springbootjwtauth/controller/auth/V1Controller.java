package com.beehyv.starter.springbootjwtauth.controller.auth;

import com.beehyv.starter.springbootjwtauth.entities.RefreshToken;
import com.beehyv.starter.springbootjwtauth.payload.request.LoginRequest;
import com.beehyv.starter.springbootjwtauth.payload.request.TokenRefreshRequest;
import com.beehyv.starter.springbootjwtauth.payload.response.JwtResponse;
import com.beehyv.starter.springbootjwtauth.payload.response.MessageResponse;
import com.beehyv.starter.springbootjwtauth.payload.response.TokenRefreshResponse;
import com.beehyv.starter.springbootjwtauth.security.jwt.JwtUtils;
import com.beehyv.starter.springbootjwtauth.security.service.RefreshTokenService;
import com.beehyv.starter.springbootjwtauth.security.service.UserDetailsImpl;
import com.beehyv.starter.springbootjwtauth.service.AuthService;
import com.beehyv.starter.springbootjwtauth.utils.exceptions.TokenRefreshException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("AuthV1Controller")
@RequestMapping("/v1/auth")
public class V1Controller {

    @Autowired
    private AuthService authService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest){
        JwtResponse jwtResponse = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }

}
