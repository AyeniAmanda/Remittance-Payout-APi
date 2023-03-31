package com.example.remittancepayoutapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemittancePayout implements Serializable {

    @NotBlank(message = "Name cannot be empty!")
    private String name;
    @NotBlank(message = "Address cannot be empty!")
    private String address;
    private String bankcode;
    private String accountnumber;
    private String mobile;
    private String country;
    private String idtype;
    private String idnumber;
    private String idexpiry;
    private String occupation;
    private String beneOccupation;
    private String beneCustRelationship;
}
