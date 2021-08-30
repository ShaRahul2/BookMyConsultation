package org.upgrad.appointmentservice.controller;

import org.upgrad.appointmentservice.model.entity.User;
import org.upgrad.appointmentservice.model.JWT.JwtTokenRequest;
import org.upgrad.appointmentservice.model.JWT.JwtTokenResponse;
import org.upgrad.appointmentservice.model.UserPrincipal;
import org.upgrad.appointmentservice.service.TokenProvider;
import org.upgrad.appointmentservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/security")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @GetMapping("/live")
    public ResponseEntity<String> checkStatus() {
        return ResponseEntity.ok("It's running live");
    }

    /*
    This endpoint will help us to generate JWT token which will help us to communicate with the endpoints
     */
    @PostMapping("/generate-token")
    public ResponseEntity generateToken(@RequestBody JwtTokenRequest tokenRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(tokenRequest.getUsername(), tokenRequest.getPassword())
        );
        String token = tokenProvider.generateToken((UserPrincipal)authentication.getPrincipal());
        LOGGER.info("Token generated {}", token);
        return ResponseEntity.ok(token);
    }

    /*
    Return all the users for Admin role requests only
     */
    @GetMapping("/user/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }
}
