package com.phase3.sportyshoes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phase3.sportyshoes.dao.ProductRepository;
import com.phase3.sportyshoes.model.Product;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    public void addProduct(Product product)
    {
        productRepository.save(product);
    }

    public void removeProductById(long id)
    {
        productRepository.deleteById(id);
    }

    public Optional<Product> getProductById(long id)
    {
        return productRepository.findById(id);
    }

    public List<Product> getAllProductsByCategoryId(int id)
    {
        return productRepository.findAllByCategory_Id(id);
    }
}
