package com.tfm.backend.data.daos;

import com.tfm.backend.data.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer > {


}
