package br.com.park.parkCheck.controller;

import br.com.park.parkCheck.dto.AuthRequestDTO;
import br.com.park.parkCheck.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/parkCheck/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthRequestDTO authRequestDTO){

        boolean authenticated = authService.authenticate(authRequestDTO.username(), authRequestDTO.password());

        if (authenticated) {
            return ResponseEntity.ok("Login realizado com sucesso!");
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login inválido.");
        }
    }

    @GetMapping("/generateApiKey")
    public ResponseEntity<String> generateApiKey(@RequestHeader("Authorization") String authHeader){
        if(!authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token format");
        }

        String tokenReceived = authHeader.replace("Bearer ", "");

        boolean tokenValid = false;

        try{
             tokenValid = authService.isTokenValid(tokenReceived);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        if(!tokenValid){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        String apiKey = authService.generateApiKey();

        return ResponseEntity.ok("ApiKey: " + apiKey);
    }

}
