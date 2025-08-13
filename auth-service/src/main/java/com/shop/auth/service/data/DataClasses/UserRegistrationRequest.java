package com.shop.auth.service.data.DataClasses;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
}
