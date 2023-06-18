package com.tfm.backend.services;

import com.tfm.backend.TestConfig;
import com.tfm.backend.data.daos.*;
import com.tfm.backend.data.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestConfig
public class CustomerServiceIT {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CustomerTypeRepository customerTypeRepository;
    @Mock
    private PlanRepository planRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        CustomerType customerTypeResidential = new CustomerType();
        customerTypeResidential.setType("residential");
        when(customerTypeRepository.findByType(Mockito.eq("residential"))).thenReturn(Optional.of(customerTypeResidential));
    }

    @Test
    void testCreateCustomer() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setMobile("1234567890");
        Plan plan = new Plan();
        when(planRepository.findByPlanNumber(Mockito.anyInt())).thenReturn(Optional.of(plan));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(new Customer());
        when(subscriptionRepository.save(Mockito.any(Subscription.class))).thenReturn(new Subscription());
        Assertions.assertDoesNotThrow(() -> {
            customerService.createCustomer(user, 1);
        });
        verify(userRepository).save(Mockito.any(User.class));
        verify(customerRepository).save(Mockito.any(Customer.class));
        verify(subscriptionRepository).save(Mockito.any(Subscription.class));
    }

    @Test
    public void testFindCustomerFromEmail() {
        String token = "sampleToken";
        User user = new User();
        Customer customer = new Customer();
        when(jwtService.userFromBearer(token)).thenReturn("sampleEmail");
        when(userRepository.findByEmail("sampleEmail")).thenReturn(Optional.of(user));
        when(customerRepository.findByUser(user)).thenReturn(Optional.of(customer));
        Stream<Customer> result = customerService.findCustomerFromEmail(token);
        verify(jwtService).userFromBearer(token);
        verify(userRepository).findByEmail("sampleEmail");
        verify(customerRepository).findByUser(user);
        assertNotNull(result);
        assertEquals(1, result.count());
    }


}
