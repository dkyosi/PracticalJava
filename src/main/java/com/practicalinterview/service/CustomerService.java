package com.practicalinterview.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practicalinterview.datalayer.Account;
import com.practicalinterview.datalayer.Customer;
import com.practicalinterview.datalayer.Role;
import com.practicalinterview.datalayer.Transaction;
import com.practicalinterview.enums.TransactionTypes;
import com.practicalinterview.enums.UserRoles;
import com.practicalinterview.repository.AccountRepository;
import com.practicalinterview.repository.CustomerRepository;
import com.practicalinterview.repository.RoleRepository;
import com.practicalinterview.repository.TransactionRepository;
import com.practicalinterview.request.CreateUserRequest;
import com.practicalinterview.request.LoginRequest;
import com.practicalinterview.response.BaseResponse;
import com.practicalinterview.response.LoginResponseWithJwt;
import com.practicalinterview.response.UserLoginResponse;
import com.practicalinterview.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerService implements UserDetailsService {
    @Autowired
    private CustomerRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }


    @Transactional
    public BaseResponse createUserAccount(CreateUserRequest request) throws JsonProcessingException {
        try {
            if (userRepository.existsByEmail(request.getEmail())) {
                return new BaseResponse(422,"Email is already in use!");
            }
            if (userRepository.existsByCustomerId(request.getCustomerId())) {
                return new BaseResponse(422,"customer Id is already registered!");
            }
            if (userRepository.existsByUsername(request.getEmail())) {
                return new BaseResponse(422,"Username is already taken!");
            }
            Customer user = new Customer(request.getFirstName(),
                    request.getLastName(), request.getEmail(),request.getCustomerId(),
                    passwordEncoder.encode(request.getPin()));
            user.setUsername(request.getCustomerId());
            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findByRoleName(UserRoles.ROLE_CUSTOMER)
                    .orElseThrow(()->new RuntimeException("Error: Role not found"));
            roles.add(role);
            user.setRoles(roles);
            user = userRepository.save(user);
            Account acc = accountRepository.save(new Account(user,0.0));
            transactionRepository.save(new Transaction(0.0, TransactionTypes.DEPOSIT,acc));

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getCustomerId(), request.getPin()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            List<String> userRoles = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).toList();
            UserLoginResponse loginResponse
                    = new UserLoginResponse(user.getId(), user.getUsername(), user.getFirstName(),
                    user.getLastName(), user.getCustomerId(), user.getEmail(), new HashSet<>(userRoles), user.isEnabled());
            return new BaseResponse(new LoginResponseWithJwt(loginResponse, 200, jwt));
        } catch (Exception e) {
            throw e;
        }
    }

    public BaseResponse loginUser(LoginRequest request) throws JsonProcessingException {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getCustomerId(), request.getPin()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            Customer user = (Customer) authentication.getPrincipal();
            List<String> roles = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).toList();
            UserLoginResponse loginResponse
                    = new UserLoginResponse(user.getId(), user.getUsername(), user.getFirstName(),
                    user.getLastName(), user.getCustomerId(), user.getEmail(), new HashSet<>(roles), user.isEnabled());
            return new BaseResponse(new LoginResponseWithJwt(loginResponse, 200, jwt));
        } catch (Exception e) {
            throw e;
        }
    }

    public BaseResponse getAllCustomers() {
        try {
            return new BaseResponse(userRepository.findAll());
        }catch (Exception e){
            throw e;
        }
    }
}
