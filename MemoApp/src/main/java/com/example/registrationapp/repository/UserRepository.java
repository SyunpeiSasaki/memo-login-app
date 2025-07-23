package com.example.registrationapp.repository; // ✅ 正しいパッケージを指定

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.registrationapp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // emailでユーザーを検索できるように修正
    Optional<User> findByEmail(String email);
}
