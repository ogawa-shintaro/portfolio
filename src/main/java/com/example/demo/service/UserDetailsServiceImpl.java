package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.demo.entity.User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("ユーザーが見つかりませんでした: " + username);
        }
        return User.builder()
            .username(user.getName())
            .password(user.getPassword())
            .roles("USER") // ここでは簡単のために全ユーザーにUSERロールを割り当てます
            .build();
    }
}
