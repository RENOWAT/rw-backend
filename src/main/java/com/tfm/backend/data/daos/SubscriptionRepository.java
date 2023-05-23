package com.tfm.backend.data.daos;

import com.tfm.backend.data.entities.Customer;
import com.tfm.backend.data.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer > {
    List<Subscription> findByCustomer(Customer customer);
    Optional<Subscription> findByCups(String cups);
}
