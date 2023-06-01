package com.tfm.backend.api.resources;

import com.tfm.backend.api.dtos.CustomerRegisterDto;
import com.tfm.backend.api.dtos.UserDto;
import com.tfm.backend.data.enums.Role;
import com.tfm.backend.services.RestClientTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.time.LocalDateTime;
import static com.tfm.backend.api.resources.CustomerResource.*;

@ApiTestConfig
public class CustomerResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testCreateCustomer() {
        String pass = new BCryptPasswordEncoder().encode("6");
        this.webTestClient
                .post()
                .uri(CUSTOMERS + CREATE)
                .bodyValue(CustomerRegisterDto.builder()
                        .userDto(UserDto.builder().mobile("123456787").firstName("Dfe").familyName("Chec")
                            .dni("55555665H").password(pass).email("dfe@hot.com").role(Role.CUSTOMER)
                            .registrationDate(LocalDateTime.now()).active(true).build())
                        .selectedPlan(1).build())
                .exchange().expectStatus().isOk();
    }
}
