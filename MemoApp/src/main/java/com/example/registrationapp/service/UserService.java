package com.example.registrationapp.service; // ✅ 正しいパッケージを指定

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.registrationapp.entity.User;
import com.example.registrationapp.repository.UserRepository;

@Service
public class UserService {

    //  Logger を設定
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ユーザー登録処理（パスワードをハッシュ化して保存）
    @Transactional
    public void registerUser(User user) {
        logger.debug("Attempting to register user: " + user.getEmail());

        // パスワードをハッシュ化
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        logger.info("User registered successfully: " + user.getEmail());
    }

    // ユーザー検索処理（email で最初の1件を取得）
    public User findByEmail(String email) {
        logger.debug("Searching for user with email: " + email);

        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            logger.info("User found: " + user.getEmail());
        } else {
            logger.warn("No user found with email: " + email);
        }

        return user;
    }
}
