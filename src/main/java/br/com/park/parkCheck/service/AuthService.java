package br.com.park.parkCheck.service;

import br.com.park.parkCheck.model.ApiKeyEntity;
import br.com.park.parkCheck.model.AuthEntity;
import br.com.park.parkCheck.model.TokenParams;
import br.com.park.parkCheck.repository.ApiKeyRepository;
import br.com.park.parkCheck.repository.AuthRepository;
import br.com.park.parkCheck.service.util.JwtTokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthService(AuthRepository authRepository, ApiKeyRepository apiKeyRepository,
                       PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil){
        this.authRepository = authRepository;
        this.apiKeyRepository = apiKeyRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public String authenticate(String username, String password) throws IllegalAccessException {
        AuthEntity authEntity = authRepository.findByUsername(username);

        if(authEntity != null && passwordEncoder.matches(password, authEntity.getPassword())){
            String token = jwtTokenUtil.generateToken(username);
            authEntity.setToken(token);
            authRepository.save(authEntity);

            return token;
        }

        throw new IllegalAccessException("Falha na autenticação.");
    }

    public String generateApiKey() {
        deleteExpiredApiKeys();

        String key = Base64.getEncoder().encodeToString(jwtTokenUtil.generatedRandomSecret());

        ApiKeyEntity apiKeyEntity = new ApiKeyEntity();
        apiKeyEntity.setKey((key));
        apiKeyEntity.setValidUntil(String.valueOf(LocalDateTime.now().plusHours(24)));

        apiKeyRepository.save(apiKeyEntity);

        return apiKeyEntity.getKey();
    }

    public boolean validateApiKey(String apiKey){
        ApiKeyEntity storedApiKey = apiKeyRepository.findByKey(apiKey);

        if(storedApiKey == null){
            return false;
        }

        LocalDateTime now = LocalDateTime.now();

        if(now.isAfter(LocalDateTime.parse(storedApiKey.getValidUntil()))){
            return false;
        }

        return true;
    }

    private void deleteExpiredApiKeys(){
        LocalDateTime now = LocalDateTime.now();
        List<ApiKeyEntity> expiredKeys = apiKeyRepository.findByValidUntilBefore(now);

        apiKeyRepository.deleteAll(expiredKeys);
    }

    public boolean isTokenValid(String tokenReceived){
        if(tokenReceived == null || tokenReceived.isEmpty()){
            return false;
        }

        TokenParams tokenParams = jwtTokenUtil.getTokenParams(tokenReceived);

        AuthEntity authEntity = authRepository.findByUsername(tokenParams.getUsername());

        if(!(tokenReceived.equals(authEntity.getToken()))){
            return false;
        }
        return true;
    }

    public void initializerAuth(){
        String username = "admin";

        Optional<AuthEntity> existingAuth = Optional.ofNullable(authRepository.findByUsername(username));

        if(existingAuth.isEmpty()){
            String encodedPassword = passwordEncoder.encode("Senh@Adm1n");

            AuthEntity authEntity = new AuthEntity(
                    "admin", //username
                    encodedPassword //password
            );

            authRepository.save(authEntity);
        }
    }
}
