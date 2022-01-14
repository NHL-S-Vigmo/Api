package com.nhlstenden.student.vigmo.controllers.security;

import com.nhlstenden.student.vigmo.dto.ScreenDto;
import com.nhlstenden.student.vigmo.security.JWTProvider;
import com.nhlstenden.student.vigmo.security.models.LoginDto;
import com.nhlstenden.student.vigmo.services.ScreenService;
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
@RequestMapping("/authenticate_screen")
@CrossOrigin(exposedHeaders = "jwt-token")
public class AuthenticateScreenController {

    private final JWTProvider jwtProvider;
    private final ScreenService screenService;

    public AuthenticateScreenController(JWTProvider jwtProvider, ScreenService screenService) {
        this.jwtProvider = jwtProvider;
        this.screenService = screenService;
    }

    @ApiOperation(value = "Endpoint used for signing in as a screen")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Signed in, jwt is in header"),
            @ApiResponse(code = 401, message = "Invalid credentials"),
            @ApiResponse(code = 403, message = "Account is disabled")
    })
    @GetMapping("/{authToken}")
    public ResponseEntity<Void> login(@PathVariable("authToken") String authToken) {
        //Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

        //fixme: problem, this is always screen 1. this is bad.!
        ScreenDto screen = screenService.get(1L);

        if(screen == null) //throw bad credentials exception
            return ResponseEntity.badRequest().build();

        //String username = ((UserDetails) authentication.getPrincipal()).getUsername();

        //String role = (String) ((UserDetails) authentication.getPrincipal()).getAuthorities().toArray()[0];
        //String profilePicture = userService.findByUsername(username).getPfpLocation();
        String token = jwtProvider.createScreenToken(screen.getName(), "ROLE_SCREEN");

        return ResponseEntity.ok()
                .header("jwt-token", token)
                .build();
    }
}
