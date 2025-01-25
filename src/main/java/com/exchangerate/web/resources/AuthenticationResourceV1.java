package com.exchangerate.web.resources;

import com.exchangerate.security.JwtUtils;
import com.exchangerate.web.dto.security.JwtRequestDTO;
import com.exchangerate.web.dto.security.JwtResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthenticationResourceV1 {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationResourceV1.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/auth-token")
    public ResponseEntity<JwtResponseDTO> authenticate (@RequestBody JwtRequestDTO jwtRequestDTO) {
        logger.info("Token issue request received for user={}", jwtRequestDTO.userName());

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequestDTO.userName(), jwtRequestDTO.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(authentication);
        JwtResponseDTO jwtResponseDTO = JwtResponseDTO.builder()
                .userName(jwtRequestDTO.userName())
                .token(token)
                .build();
        return ResponseEntity.ok(jwtResponseDTO);
    }
}
