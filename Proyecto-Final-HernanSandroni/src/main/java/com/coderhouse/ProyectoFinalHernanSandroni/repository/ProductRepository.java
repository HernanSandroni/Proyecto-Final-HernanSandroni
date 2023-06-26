package com.coderhouse.ProyectoFinalHernanSandroni.repository;


import com.coderhouse.ProyectoFinalHernanSandroni.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}