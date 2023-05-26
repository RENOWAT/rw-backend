package com.tfm.backend.api.resources;

import com.tfm.backend.api.dtos.SubscriptionDto;
import com.tfm.backend.services.CustomerService;
import com.tfm.backend.services.SubscriptionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping(SubscriptionResource.SUBSCRIPTION)
public class SubscriptionResource {

    public static final String SUBSCRIPTION = "/subscription";
    private final SubscriptionService subscriptionService;
    private final CustomerService customerService;

    @Autowired
    public SubscriptionResource(CustomerService customerService,SubscriptionService subscriptionService) {
        this.customerService = customerService;
        this.subscriptionService = subscriptionService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public Stream<SubscriptionDto> findSubscriptionsByCustomer(
            @RequestHeader(value = "Authorization") String token) {
        return this.customerService.findCustomerFromEmail(token)
                .flatMap(this.subscriptionService::findByCustomer)
                .map(SubscriptionDto::new);
    }


}
