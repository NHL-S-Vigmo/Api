package com.nhlstenden.student.vigmo.controllers.security;

import com.nhlstenden.student.vigmo.security.JWTProvider;
import com.nhlstenden.student.vigmo.security.models.LoginDto;
import com.nhlstenden.student.vigmo.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
@CrossOrigin(exposedHeaders = "jwt-token")
public class AuthenticateController {

    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final UserService userService;

    public AuthenticateController(AuthenticationManager authenticationManager, JWTProvider jwtProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> login(@RequestBody LoginDto login) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

        String username = ((UserDetails) authentication.getPrincipal()).getUsername();

        String role = ((UserDetails) authentication.getPrincipal()).getAuthorities().toArray()[0].toString();
        String profilePicture = userService.findByUsername(username).getPfpLocation();
        String token = jwtProvider.createToken(username, role, profilePicture);

        return ResponseEntity.ok()
                .header("jwt-token", token)
                .build();
    }
}
