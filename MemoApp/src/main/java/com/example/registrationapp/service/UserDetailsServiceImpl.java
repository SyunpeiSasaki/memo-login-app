package com.example.registrationapp.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.registrationapp.entity.User;
import com.example.registrationapp.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // ✅ `username` → `email` に変更
        User user = userRepository.findByEmail(email) // `findFirstByName(username)` → `findByEmail(email)` に変更
                .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません: " + email));
        
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // `getName()` → `getEmail()` に変更
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")) // ユーザーロールを設定
        );
    }
}
