package com.beehyv.starter.springbootjwtauth.service;

import com.beehyv.starter.springbootjwtauth.entities.RefreshToken;
import com.beehyv.starter.springbootjwtauth.enums.ERole;
import com.beehyv.starter.springbootjwtauth.payload.response.JwtResponse;
import com.beehyv.starter.springbootjwtauth.security.jwt.JwtUtils;
import com.beehyv.starter.springbootjwtauth.security.service.RefreshTokenService;
import com.beehyv.starter.springbootjwtauth.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    public JwtResponse authenticate(String username, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<ERole> roles = userDetails.getAuthorities().stream().map(item -> ERole.valueOf(item.getAuthority()))
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles);
    }
}
