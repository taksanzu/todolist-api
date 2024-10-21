package org.tak.todolistapi.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private List<String> roles;

    public JwtResponse(String jwtToken, String username, List<String> roles) {
        this.token = jwtToken;
        this.username = username;
        this.roles = roles;
    }
}