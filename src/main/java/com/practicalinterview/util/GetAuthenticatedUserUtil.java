package com.practicalinterview.util;

import com.practicalinterview.datalayer.Customer;
import com.practicalinterview.facades.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetAuthenticatedUserUtil {
    @Autowired
    private AuthenticationFacade authenticationFacade;
    public Customer getCurrentUser(){
        return (Customer) authenticationFacade.getAuthentication().getPrincipal();
    }
}
