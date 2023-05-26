package com.tfm.backend.services;

import com.tfm.backend.data.daos.SubscriptionRepository;
import com.tfm.backend.data.entities.Customer;
import com.tfm.backend.data.entities.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Stream<Subscription> findByCustomer(Customer customer) {
        return this.subscriptionRepository.findByCustomer(customer).stream();
    }

}
