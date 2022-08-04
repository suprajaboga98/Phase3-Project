package com.phase3.sportyshoes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phase3.sportyshoes.model.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
    
}
