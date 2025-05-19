package com.beehyv.starter.springbootjwtauth.service;

import com.beehyv.starter.springbootjwtauth.entities.Role;
import com.beehyv.starter.springbootjwtauth.entities.User;
import com.beehyv.starter.springbootjwtauth.enums.ERole;
import com.beehyv.starter.springbootjwtauth.payload.request.SignupRequest;
import com.beehyv.starter.springbootjwtauth.repository.RoleRepository;
import com.beehyv.starter.springbootjwtauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    public void registerUser(SignupRequest signUpRequest){

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<ERole> signupRole = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (signupRole == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            signupRole.forEach(role -> {
                Role userRole = roleRepository.findByName(role)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

}
