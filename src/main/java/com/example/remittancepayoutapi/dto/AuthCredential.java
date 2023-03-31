package com.example.remittancepayoutapi.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthCredential {
    private String access_token;
    private Long created_at;
    private Long expires_in;
}