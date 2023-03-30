package com.example.remittancepayoutapi.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthCredential {
    private String accessToken;
    private Long expiresIn;
    private Long requestedAt;
}