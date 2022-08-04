package com.phase3.sportyshoes.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.phase3.sportyshoes.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    
    Optional<User> findUserByEmail(String email);

    @Query("select u from User u where u.email = :email")
    public User getUserByUserName(@Param("email") String email);    
}
