package com.example.registrationapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.registrationapp.entity.Memo;
import com.example.registrationapp.entity.User;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {

    //  Find Memo by its ID (already correct)
    @Query("SELECT m FROM Memo m WHERE m.id = :id")
    Memo findMemoById(@Param("id") Long id);

    //  Find Memos associated with a specific User
    @Query("SELECT m FROM Memo m WHERE m.user = :user ORDER BY m.createdAt DESC")
    List<Memo> findByUser(@Param("user") User user);
}
