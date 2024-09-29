package br.com.park.parkCheck.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class TokenParams {
    @NonNull
    private String username;
    private String token;
    private String secretKey;
}
