package com.example.transportcompany.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Data
@Component
public class JwtConfig {
    private String secretKey = "rgvfdscdzx";
    private Integer accessTokenExpiration = 60000000;
    private Integer refreshTokenExpiration = 12000000;
}
