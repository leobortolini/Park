package br.com.park.authentication.init;

import br.com.park.authentication.service.AuthenticationService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class Initializer {

    private final AuthenticationService authenticationService;

    public Initializer(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    public void initializer(){
        authenticationService.initializerAuth();
    }
}
