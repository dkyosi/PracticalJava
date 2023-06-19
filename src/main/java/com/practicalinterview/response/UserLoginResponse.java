package com.practicalinterview.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {
    private long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String customerId;
    private String email;
    private Set<String> roles;
    private boolean isEnabled;
}
