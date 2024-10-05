package br.com.park.authentication.service;

public interface AuthenticationService {
    Token authenticate(String username, String password) throws IllegalAccessException;
    APIKey generateApiKey();
    void validateApiKey(String apiKey);
    boolean isTokenValid(String tokenReceived);
    void initializerAuth();

    record Token(String token) { }
    record APIKey(String apiKey) { }
}
