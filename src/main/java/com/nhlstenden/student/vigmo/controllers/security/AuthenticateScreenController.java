package com.nhlstenden.student.vigmo.controllers.security;

import com.nhlstenden.student.vigmo.dto.ScreenDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.security.JWTProvider;
import com.nhlstenden.student.vigmo.services.ScreenService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
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
        ScreenDto screen;

        try {
            screen = screenService.getScreenByAuthKey(authToken);
        } catch (DataNotFoundException dnf) {
            return ResponseEntity.status(401).build();
        }

        //create the new JWT with static role ROLE_SCREEN
        String token = jwtProvider.createScreenToken(screen.getName(), "ROLE_SCREEN");

        return ResponseEntity.ok()
                .header("jwt-token", token)
                .build();
    }
}
