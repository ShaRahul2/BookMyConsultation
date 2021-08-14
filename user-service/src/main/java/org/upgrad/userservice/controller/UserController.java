package org.upgrad.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.upgrad.userservice.model.JWT.JwtTokenRequest;
import org.upgrad.userservice.model.UserPrincipal;
import org.upgrad.userservice.model.entity.User;
import org.upgrad.userservice.service.user.TokenProvider;
import org.upgrad.userservice.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/security")
public class UserController {

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

    @PostMapping("/generate-token")
    public ResponseEntity generateToken(@RequestBody JwtTokenRequest tokenRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(tokenRequest.getUsername(), tokenRequest.getPassword())
        );
        String token = tokenProvider.generateToken((UserPrincipal) authentication.getPrincipal());
        //LOGGER.info("Token generated {}", token);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/user/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }
}


