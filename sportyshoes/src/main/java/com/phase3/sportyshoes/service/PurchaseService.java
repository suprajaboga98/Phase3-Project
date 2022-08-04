package com.phase3.sportyshoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phase3.sportyshoes.dao.PurchaseRepository;
import com.phase3.sportyshoes.model.Purchase;

@Service
public class PurchaseService {

    @Autowired
    PurchaseRepository purchaseRepository;

    public List<Purchase> getAllPurchases()
    {
        return purchaseRepository.findAll();
    }
}
