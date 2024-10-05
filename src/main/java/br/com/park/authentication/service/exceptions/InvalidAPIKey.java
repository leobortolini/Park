package br.com.park.authentication.service.exceptions;

public class InvalidAPIKey extends RuntimeException {
    public InvalidAPIKey(String message) {
        super(message);
    }
}
