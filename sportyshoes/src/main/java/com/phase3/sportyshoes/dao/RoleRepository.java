package com.phase3.sportyshoes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phase3.sportyshoes.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    
}
