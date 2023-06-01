package com.tfm.backend.data.daos;

import com.tfm.backend.data.entities.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerTypeRepository extends JpaRepository<CustomerType, Integer > {
    Optional< CustomerType > findByType(String type);

}
