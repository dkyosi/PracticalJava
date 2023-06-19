package com.practicalinterview.repository;

import com.practicalinterview.datalayer.Role;
import com.practicalinterview.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(UserRoles roleCustomer);
}
