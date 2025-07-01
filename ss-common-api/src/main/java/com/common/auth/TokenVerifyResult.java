package com.common.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenVerifyResult {
    private boolean isValid;
    private String username;
    private UserDetails userDetails;
    private UsernamePasswordAuthenticationToken authentication;
}
