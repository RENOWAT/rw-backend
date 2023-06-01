package com.tfm.backend.api.resources;

import com.tfm.backend.api.dtos.CustomerRegisterDto;
import com.tfm.backend.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(CustomerResource.CUSTOMERS)
public class CustomerResource {
    public static final String CUSTOMERS = "/customers";
    public static final String CREATE = "/create";
    private final CustomerService customerService;

    @Autowired
    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(CREATE)
    public void createCustomer(@Valid @RequestBody CustomerRegisterDto customerRegisterDto) {
        this.customerService.createCustomer(customerRegisterDto.getUserDto().toUser(),  customerRegisterDto.getSelectedPlan());
    }

}
