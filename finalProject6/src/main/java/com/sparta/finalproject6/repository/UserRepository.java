package com.sparta.finalproject6.repository;

import com.sparta.finalproject6.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
