package com.practicalinterview.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @ApiModelProperty(example = "userFirst")
    private String firstName;
    @ApiModelProperty(example = "userLast")
    private String lastName;
    @ApiModelProperty(example = "user@gmail.com")
    private String email;
    @ApiModelProperty(example = "Cust1001")
    private String customerId;
    @ApiModelProperty(example = "1234")
    private String pin;
}