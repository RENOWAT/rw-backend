package com.tfm.backend.services;

import com.tfm.backend.api.dtos.SubscriptionDto;
import com.tfm.backend.data.daos.PlanRepository;
import com.tfm.backend.data.daos.SubscriptionRepository;
import com.tfm.backend.data.entities.Customer;
import com.tfm.backend.data.entities.Plan;
import com.tfm.backend.data.entities.Subscription;
import com.tfm.backend.services.exceptions.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Stream;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository,PlanRepository planRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
    }

    public Stream<Subscription> findByCustomer(Customer customer) {
        return this.subscriptionRepository.findByCustomer(customer).stream();
    }

    public void createSubscription(SubscriptionDto subscriptionDto, Customer customer) {
        this.assertNoExistByCups(subscriptionDto.getCups());
        Plan plan = planRepository.findByName(subscriptionDto.getPlanName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid plan"));
        this.subscriptionRepository.save(Subscription.builder().customer(customer).plan(plan)
                .address(subscriptionDto.getAddress()).cups(subscriptionDto.getCups())
                .subscription_start_date(LocalDate.now()).subscription_end_date(LocalDate.of(2999,12,31))
                .build());
    }

    private void assertNoExistByCups(String cups) {
        if (this.subscriptionRepository.findByCups(cups).isPresent()) {
            throw new ConflictException("Cups already exists: " + cups);
        }
    }

}
