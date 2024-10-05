package br.com.park.authentication.service;

import br.com.park.authentication.model.ApiKeyEntity;
import br.com.park.authentication.model.AuthEntity;
import br.com.park.authentication.model.TokenParams;
import br.com.park.authentication.repository.ApiKeyRepository;
import br.com.park.authentication.repository.AuthRepository;
import br.com.park.authentication.service.exceptions.InvalidAPIKey;
import br.com.park.authentication.service.util.JwtTokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthRepository authRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthenticationServiceImpl(AuthRepository authRepository, ApiKeyRepository apiKeyRepository,
                                     PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil){
        this.authRepository = authRepository;
        this.apiKeyRepository = apiKeyRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public Token authenticate(String username, String password) throws IllegalAccessException {
        AuthEntity authEntity = authRepository.findByUsername(username);

        if(authEntity != null && passwordEncoder.matches(password, authEntity.getPassword())){
            String token = jwtTokenUtil.generateToken(username);
            authEntity.setToken(token);
            authRepository.save(authEntity);

            return new Token("Bearer " + token);
        }

        throw new IllegalAccessException("Falha na autenticação.");
    }

    public APIKey generateApiKey() {
        deleteExpiredApiKeys();

        String key = Base64.getEncoder().encodeToString(jwtTokenUtil.generatedRandomSecret());

        ApiKeyEntity apiKeyEntity = new ApiKeyEntity();
        apiKeyEntity.setKey((key));
        apiKeyEntity.setValidUntil(String.valueOf(LocalDateTime.now().plusHours(4)));

        apiKeyRepository.save(apiKeyEntity);

        return new APIKey(apiKeyEntity.getKey());
    }

    public void validateApiKey(String apiKey){
        ApiKeyEntity storedApiKey = apiKeyRepository.findByKey(apiKey);

        if(storedApiKey == null){
            throw new InvalidAPIKey("Invalid API Key");
        }

        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(LocalDateTime.parse(storedApiKey.getValidUntil())))
            throw new InvalidAPIKey("Invalid API Key");
    }

    private void deleteExpiredApiKeys(){
        LocalDateTime now = LocalDateTime.now();
        List<ApiKeyEntity> expiredKeys = apiKeyRepository.findByValidUntilBefore(now);

        apiKeyRepository.deleteAll(expiredKeys);
    }

    public boolean isTokenValid(String tokenReceived) {
        if(tokenReceived == null || tokenReceived.isEmpty()) {
            return false;
        }
        TokenParams tokenParams = jwtTokenUtil.getTokenParams(tokenReceived);
        AuthEntity authEntity = authRepository.findByUsername(tokenParams.getUsername());

        return tokenReceived.equals(authEntity.getToken());
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
