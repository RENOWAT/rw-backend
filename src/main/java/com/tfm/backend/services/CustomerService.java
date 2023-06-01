package com.tfm.backend.services;
import com.tfm.backend.data.daos.*;
import com.tfm.backend.data.entities.*;
import com.tfm.backend.services.exceptions.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class CustomerService {

    private final JwtService jwtService;
    private final CustomerRepository customerRepository;
    private final CustomerTypeRepository customerTypeRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public CustomerService(JwtService jwtService, CustomerRepository customerRepository,
                           UserRepository userRepository, CustomerTypeRepository customerTypeRepository,
                           PlanRepository planRepository, SubscriptionRepository subscriptionRepository) {
        this.jwtService = jwtService;
        this.customerRepository = customerRepository;
        this.customerTypeRepository = customerTypeRepository;
        this.planRepository = planRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    public void createCustomer(User user, Integer selectedPlan){
        this.assertNoExistByEmail(user.getEmail());
        this.assertNoExistByMobile(user.getMobile());
        CustomerType customerTypeResidential = customerTypeRepository.findByType("residential")
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer type"));
        Plan plan = planRepository.findByPlanNumber(selectedPlan)
                .orElseThrow(() -> new IllegalArgumentException("Invalid plan"));
        user.setRegistrationDate(LocalDateTime.now());
        this.userRepository.save(user);
        Customer customer = Customer.builder().customerType(customerTypeResidential).user(user)
                .build();
        this.customerRepository.save(customer);
        this.subscriptionRepository.save(Subscription.builder().customer(customer).plan(plan)
                .address(customer.getUser().getAddress()).subscription_start_date(LocalDate.now().plusMonths(1).withDayOfMonth(1))
                .subscription_end_date(LocalDate.of(2999, 12, 31))
                .cups(this.generateCups()).build());

    }

    public Stream<Customer> findCustomerFromEmail(String token){
        User user = userRepository.findByEmail(jwtService.userFromBearer(token))
                .orElseThrow(() -> new UsernameNotFoundException("email not found. "));
        return this.customerRepository.findByUser(user).stream();
    }

    private void assertNoExistByEmail(String email) {
        if (this.userRepository.findByEmail(email).isPresent()) {
            throw new ConflictException("The email already exists: " + email);
        }
    }

    private void assertNoExistByMobile(String mobile) {
        if (this.userRepository.findByMobile(mobile).isPresent()) {
            throw new ConflictException("The mobile already exists: " + mobile);
        }
    }

    private String generateCups() {
        String fixedPart = "ES002100000";
        String numbers = "0123456789";
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();

        String randomPart = IntStream.range(0, 7)
                .mapToObj(i -> String.valueOf(numbers.charAt(random.nextInt(numbers.length()))))
                .collect(Collectors.joining());

        randomPart += IntStream.range(0, 2)
                .mapToObj(i -> String.valueOf(letters.charAt(random.nextInt(letters.length()))))
                .collect(Collectors.joining());

        return fixedPart + randomPart;
    }

}
