package com.jaeeun.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jaeeun.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // email로 사용자 정보를 가져 옴
}
