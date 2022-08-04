package com.phase3.sportyshoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.phase3.sportyshoes.dao.UserRepository;
import com.phase3.sportyshoes.model.CustomUserDetail;
import com.phase3.sportyshoes.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //fetch user data from database
        User user = userRepository.getUserByUserName(username);
        if(user == null)
        {
            throw new UsernameNotFoundException("can't find user!!");
        }
        CustomUserDetail customUserDetails = new CustomUserDetail(user);
        return customUserDetails;
    }   
}
