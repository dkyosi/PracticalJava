package com.practicalinterview.datalayer;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "customers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"customerId"}),
        @UniqueConstraint(columnNames = {"email"})})
public class Customer implements UserDetails {
    @Id
    @SequenceGenerator(name = "customer_sequence_generator",
            sequenceName = "customer_sequence_generator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_sequence_generator")
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String customerId;
    private String pin;

    private String username;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Customer(String firstName, String lastName, String email, String customerId, String pin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.customerId = customerId;
        this.pin = pin;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return pin;
    }

    @Override
    public String getUsername() {
        return customerId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
