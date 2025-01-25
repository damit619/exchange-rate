package com.exchangerate.web.dto.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JwtResponseDTO {
    private String token;
    private String type;
    private String userName;

    public String getType () {
        return "Bearer";
    }
}
