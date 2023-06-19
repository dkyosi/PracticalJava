package com.practicalinterview.seeders;

import com.practicalinterview.datalayer.Role;
import com.practicalinterview.enums.UserRoles;
import com.practicalinterview.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ApplicationSeeder {
    @Autowired
    private RoleRepository roleRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void seedRoles(){
        if (roleRepository.count()==0){
            roleRepository.saveAll(Arrays.asList(
                    new Role(UserRoles.ROLE_ADMIN),
                    new Role(UserRoles.ROLE_CUSTOMER)
            ));
        }
    }
}