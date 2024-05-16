package com.javaee.onlinehosbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministratorResponse {
    private String AdministratorId;

    private String Name;

    private boolean Gender;

    private String Contact;

    private String Password;

    public boolean getGender() {
        return this.Gender;
    }
}
