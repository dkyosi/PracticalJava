package com.practicalinterview.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseWithJwt {
    private UserLoginResponse profile;
    private int status;
    private String token;
}