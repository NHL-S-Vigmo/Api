package com.nhlstenden.student.vigmo.controllers.security;

import com.nhlstenden.student.vigmo.dto.UserDto;
import com.nhlstenden.student.vigmo.security.JWTProvider;
import com.nhlstenden.student.vigmo.security.models.LoginDto;
import com.nhlstenden.student.vigmo.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Endpoint used for signing in as a user.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Signed in, jwt is in header"),
            @ApiResponse(code = 401, message = "Invalid credentials"),
            @ApiResponse(code = 403, message = "Account is disabled")
    })
    @PostMapping
    public ResponseEntity<Void> login(@RequestBody LoginDto login) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        UserDto userByUsername = userService.findByUsername(username);

        long id = userByUsername.getId();
        String role = ((UserDetails) authentication.getPrincipal()).getAuthorities().toArray()[0].toString();
        String profilePicture = userByUsername.getPfpLocation();
        String token = jwtProvider.createToken(id, username, role, profilePicture);

        return ResponseEntity.ok()
                .header("jwt-token", token)
                .build();
    }
}
