package com.example.demo.service;

import com.example.demo.data.entity.Users;
import com.example.demo.data.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> allUsers() {
       try{
           logger.debug("Getting all users");
           List<Users> users = new ArrayList<>();
           users.addAll(userRepository.findAll());
           return users;
       }
       catch (Exception e){
           throw new RuntimeException(e.getMessage());
       }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Getting user by username: " + username);
           return this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Users getCurrentUser() throws UsernameNotFoundException {
       try{
           logger.debug("Getting user by Current Session:");
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           return  (Users) authentication.getPrincipal();
       }catch (Exception e){
           throw new UsernameNotFoundException("User not found");
       }
    }

}