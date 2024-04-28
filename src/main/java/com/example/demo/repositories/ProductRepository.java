package com.example.demo.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.ProductsModel;

@Repository
public interface ProductRepository extends JpaRepository<ProductsModel, UUID> {

}
