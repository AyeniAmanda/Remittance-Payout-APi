package com.example.remittancepayoutapi.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthCredential implements Serializable {
    private String access_token;
    private Long created_at;
    private Long expires_in;
}