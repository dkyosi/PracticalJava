package com.practicalinterview.controller;

import com.practicalinterview.request.CreateUserRequest;
import com.practicalinterview.request.LoginRequest;
import com.practicalinterview.response.BaseResponse;
import com.practicalinterview.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth/users")
public class CustomerController {
    @Autowired
    private CustomerService userService;

    @PostMapping("/register")
    public BaseResponse createUser(@RequestBody CreateUserRequest request){
        try {
            return userService.createUserAccount(request);
        }catch (Exception e){
            return new BaseResponse(400,"Failed to create account"+e.getMessage());
        }
    }
    @PostMapping("/login")
    public BaseResponse loginUser(@RequestBody LoginRequest request){
        try {
            return userService.loginUser(request);
        }catch (Exception e){
            return new BaseResponse(400,"Failed to login");
        }
    }

    @GetMapping
    public BaseResponse getAllCustomers(){
        try {
            return new BaseResponse(userService.getAllCustomers());
        }catch (Exception e){
            return new BaseResponse(400,"Failed to get customers");
        }
    }
}
