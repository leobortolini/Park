package br.com.park.authentication.controller;

import br.com.park.authentication.dto.AuthRequestDTO;
import br.com.park.authentication.service.AuthenticationService;
import br.com.park.authentication.service.AuthenticationService.APIKey;
import br.com.park.authentication.service.AuthenticationService.Token;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@Valid @RequestBody AuthRequestDTO authRequestDTO) throws IllegalAccessException {
        Token token = authenticationService.authenticate(authRequestDTO.username(), authRequestDTO.password());

        return ResponseEntity.ok(token);
    }

    @GetMapping("/generateApiKey")
    public ResponseEntity<APIKey> generateApiKey(@RequestHeader("Authorization") String authHeader){
        if(!authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String tokenReceived = authHeader.replace("Bearer ", "");

        boolean tokenValid;

        try{
             tokenValid = authenticationService.isTokenValid(tokenReceived);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if(!tokenValid){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        APIKey apiKey = authenticationService.generateApiKey();

        return ResponseEntity.ok(apiKey);
    }

}
