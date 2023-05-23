package com.tfm.backend.data.daos;

import com.tfm.backend.data.entities.Customer;
import com.tfm.backend.data.entities.CustomerType;
import com.tfm.backend.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer > {
    Optional<Customer> findByCustomerType(CustomerType customerType);
    Optional<Customer> findByUser(User user);

}
