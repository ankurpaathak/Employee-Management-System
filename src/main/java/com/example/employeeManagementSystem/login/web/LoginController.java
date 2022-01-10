package com.example.employeeManagementSystem.login.web;

import com.example.employeeManagementSystem.common.security.JwtUtility;
import com.example.employeeManagementSystem.login.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtility jwtUtility;


    @PostMapping(value = "/auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getUsername());

        final String jwt = jwtUtility.generateToken(userDetails);


        return ResponseEntity.ok(new LoginResponseDto(jwt));
    }

}