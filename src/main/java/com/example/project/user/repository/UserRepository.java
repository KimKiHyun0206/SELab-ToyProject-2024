package com.example.project.user.repository;

import com.example.project.user.domain.User;
import com.example.project.user.domain.vo.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(Email email);
    Optional<User> findByUserId(String userId);
}
