package br.com.park.parkCheck.init;

import br.com.park.parkCheck.service.AuthService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class Initializer {

    private final AuthService authService;

    public Initializer(AuthService authService){
        this.authService = authService;
    }

    @PostConstruct
    public void initializer(){
        authService.initializerAuth();
    }
}
