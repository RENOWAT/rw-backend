package com.tfm.backend.data.daos;

import com.tfm.backend.data.entities.*;
import com.tfm.backend.data.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Repository // @Profile("dev")
public class UserSeederDev {

    private DatabaseStarting databaseStarting;
    private UserRepository userRepository;
    private CustomerTypeRepository customerTypeRepository;
    private CustomerRepository customerRepository;

    private TariffRepository tariffRepository;
    private ProductRepository productRepository;
    private PlanRepository planRepository;
    private SubscriptionRepository subscriptionRepository;
    private InvoiceRepository invoiceRepository;



    @Autowired
    public UserSeederDev(UserRepository userRepository, DatabaseStarting databaseStarting,
                         CustomerTypeRepository customerTypeRepository, CustomerRepository customerRepository,
                         ProductRepository productRepository, PlanRepository planRepository,
                         SubscriptionRepository subscriptionRepository, InvoiceRepository invoiceRepository,
                         TariffRepository tariffRepository) {
        this.userRepository = userRepository;
        this.customerTypeRepository = customerTypeRepository;
        this.customerRepository = customerRepository;
        this.tariffRepository = tariffRepository;
        this.productRepository = productRepository;
        this.planRepository = planRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.invoiceRepository = invoiceRepository;
        this.databaseStarting = databaseStarting;
        this.deleteAllAndInitializeAndSeedDataBase();
    }

    public void deleteAllAndInitializeAndSeedDataBase() {
        this.deleteAllAndInitialize();
        this.seedDataBase();
    }

    public void deleteAllAndInitialize() {
        this.invoiceRepository.deleteAll();
        this.subscriptionRepository.deleteAll();
        this.planRepository.deleteAll();
        this.customerRepository.deleteAll();
        this.customerTypeRepository.deleteAll();
        this.productRepository.deleteAll();
        this.userRepository.deleteAll();

        LogManager.getLogger(this.getClass()).warn("------- Deleted All -----------");
        this.databaseStarting.initialize();
    }

    private void seedDataBase() {
        LogManager.getLogger(this.getClass()).warn("------- Initial Load from JAVA -----------");
        String pass = new BCryptPasswordEncoder().encode("6");
        User[] users = {
                User.builder().mobile("666666000").firstName("adm").password(pass).dni(null).address("C/TPV, 0")
                        .email("adm@gmail.com").role(Role.ADMIN).registrationDate(LocalDateTime.now()).active(true)
                        .build(),
                User.builder().mobile("666666001").firstName("man").password(pass).dni("66666601C").address("C/TPV, 1")
                        .email("man@gmail.com").role(Role.MANAGER).registrationDate(LocalDateTime.now()).active(true)
                        .build(),
                User.builder().mobile("666666002").firstName("ope").password(pass).dni("66666602K").address("C/TPV, 2")
                        .email("ope@gmail.com").role(Role.OPERATOR).registrationDate(LocalDateTime.now()).active(true)
                        .build(),
                User.builder().mobile("666666003").firstName("c1").familyName("ac1").password(pass).dni("66666603E")
                        .address("C/TPV, 3").email("c1@gmail.com").role(Role.CUSTOMER)
                        .registrationDate(LocalDateTime.now()).active(true).build(),
                User.builder().mobile("666666004").firstName("c2").familyName("ac2").password(pass).dni("66666604T")
                        .address("C/TPV, 4").email("c2@gmail.com").role(Role.CUSTOMER)
                        .registrationDate(LocalDateTime.now()).active(true).build(),
                User.builder().mobile("666666005").firstName("c3").email("e2@gmail.com").password(pass).role(Role.CUSTOMER)
                        .registrationDate(LocalDateTime.now()).active(true).build(),
                User.builder().mobile("66").firstName("customer").email("d2@gmail.com").password(pass).role(Role.CUSTOMER)
                        .registrationDate(LocalDateTime.now()).active(true).build(),
                User.builder().mobile("123456789").address("c/ test 1").firstName("abc").email("abc@gmail.com").password(pass).email("abc@hot.com").role(Role.CUSTOMER)
                        .registrationDate(LocalDateTime.now()).active(true).build(),
                User.builder().mobile("123456788").address("c/ test 2").firstName("Cdb").familyName("Cheic")
                        .dni("55555665H").password(pass).email("cdb@hot.com").role(Role.CUSTOMER)
                        .registrationDate(LocalDateTime.now()).active(true).build(),
        };
        this.userRepository.saveAll(Arrays.asList(users));
        LogManager.getLogger(this.getClass()).warn("        ------- users");

        CustomerType[] customerTypes = {
                CustomerType.builder().type("residential")
                        .build(),
        };
        this.customerTypeRepository.saveAll(Arrays.asList(customerTypes));
        LogManager.getLogger(this.getClass()).warn("        ------- customerTypes");

        Customer[] customers = {
                Customer.builder().customerType(customerTypes[0]).user(users[7])
                        .build(),
                Customer.builder().customerType(customerTypes[0]).user(users[8])
                        .build(),
        };
        this.customerRepository.saveAll(Arrays.asList(customers));
        LogManager.getLogger(this.getClass()).warn("        ------- customers");


        Tariff[] tariffs = {
                Tariff.builder().name("BT 2.0 TD").build(),
        };
        this.tariffRepository.saveAll(Arrays.asList(tariffs));
        LogManager.getLogger(this.getClass()).warn("        ------- tariffs");

        Product[] products = {
                Product.builder().type("electricidad").tariff(tariffs[0])
                        .description("electricidad origen convencional")
                        .build(),
        };
        this.productRepository.saveAll(Arrays.asList(products));
        LogManager.getLogger(this.getClass()).warn("        ------- products");

        Plan[] plans = {
                Plan.builder().name("plan estable").planNumber(1)
                        .p1(BigDecimal.valueOf(0.1384)).p2(BigDecimal.valueOf(0.1384)).p3(BigDecimal.valueOf(0.1384))
                        .tf(BigDecimal.valueOf(3.45)).ptf(BigDecimal.valueOf(2.63268)).product(products[0])
                        .build(),
                Plan.builder().name("plan noche").planNumber(2)
                        .p1(BigDecimal.valueOf(0.055)).p2(BigDecimal.valueOf(0.151)).p3(BigDecimal.valueOf(0.163))
                        .tf(BigDecimal.valueOf(3.45)).ptf(BigDecimal.valueOf(2.63268)).product(products[0])
                        .build(),
        };
        this.planRepository.saveAll(Arrays.asList(plans));
        LogManager.getLogger(this.getClass()).warn("        ------- plans");

        Subscription[] subscriptions = {
                Subscription.builder().subscription_start_date(LocalDate.of(2022, 1, 8))
                        .subscription_end_date(LocalDate.of(2999, 12, 31))
                        .address(customers[0].getUser().getAddress())
                        .cups("ES0021000005307136XK")
                        .plan(plans[0]).customer(customers[0])
                        .build(),
                Subscription.builder().subscription_start_date(LocalDate.of(2022, 12, 11))
                        .subscription_end_date(LocalDate.of(2999, 12, 31))
                        .address(customers[1].getUser().getAddress())
                        .cups("ES0021000005407137XK")
                        .plan(plans[0]).customer(customers[1])
                        .build(),
                Subscription.builder().subscription_start_date(LocalDate.of(2022, 12, 17))
                        .subscription_end_date(LocalDate.of(2999, 12, 31))
                        .address("c/ test 3")
                        .cups("ES0021000005407138XK")
                        .plan(plans[1]).customer(customers[1])
                        .build(),
        };
        this.subscriptionRepository.saveAll(Arrays.asList(subscriptions));
        LogManager.getLogger(this.getClass()).warn("        ------- subscriptions");

        Invoice[] invoices = {
                Invoice.builder().invoiceStartDate(LocalDate.of(2023, 1, 1))
                        .invoiceEndDate(LocalDate.of(2023, 1, 30))
                        .consumption(BigDecimal.valueOf(266))
                        .c1(BigDecimal.valueOf(11)).c2(BigDecimal.valueOf(125)).c3(BigDecimal.valueOf(130))
                        .amount(BigDecimal.valueOf(48.77)).currency("euro")
                        .invoiceDue(LocalDate.of(2023, 1, 15)).invoicePaid(false)
                        .subscription(subscriptions[0])
                        .build(),
                Invoice.builder().invoiceStartDate(LocalDate.of(2023, 2, 1))
                        .invoiceEndDate(LocalDate.of(2023, 2, 28))
                        .consumption(BigDecimal.valueOf(270))
                        .c1(BigDecimal.valueOf(12)).c2(BigDecimal.valueOf(120)).c3(BigDecimal.valueOf(138))
                        .amount(BigDecimal.valueOf(48.19)).currency("euro")
                        .invoiceDue(LocalDate.of(2023, 2, 15)).invoicePaid(true)
                        .subscription(subscriptions[0])
                        .build(),
                Invoice.builder().invoiceStartDate(LocalDate.of(2022, 6, 1))
                        .invoiceEndDate(LocalDate.of(2022, 6, 30))
                        .consumption(BigDecimal.valueOf(253))
                        .c1(BigDecimal.valueOf(15)).c2(BigDecimal.valueOf(122)).c3(BigDecimal.valueOf(116))
                        .amount(BigDecimal.valueOf(46.30)).currency("euro")
                        .invoiceDue(LocalDate.of(2022, 6, 15)).invoicePaid(false)
                        .subscription(subscriptions[1])
                        .build(),
                Invoice.builder().invoiceStartDate(LocalDate.of(2022, 7, 1))
                        .invoiceEndDate(LocalDate.of(2022, 7, 30))
                        .consumption(BigDecimal.valueOf(290))
                        .c1(BigDecimal.valueOf(16)).c2(BigDecimal.valueOf(136)).c3(BigDecimal.valueOf(138))
                        .amount(BigDecimal.valueOf(51.68)).currency("euro")
                        .invoiceDue(LocalDate.of(2022, 7, 15)).invoicePaid(false)
                        .subscription(subscriptions[1])
                        .build(),
                Invoice.builder().invoiceStartDate(LocalDate.of(2022, 8, 1))
                        .invoiceEndDate(LocalDate.of(2022, 8, 30))
                        .consumption(BigDecimal.valueOf(281))
                        .c1(BigDecimal.valueOf(11)).c2(BigDecimal.valueOf(124)).c3(BigDecimal.valueOf(146))
                        .amount(BigDecimal.valueOf(50.37)).currency("euro")
                        .invoiceDue(LocalDate.of(2022, 8, 15)).invoicePaid(false)
                        .subscription(subscriptions[1])
                        .build(),
                Invoice.builder().invoiceStartDate(LocalDate.of(2022, 9, 1))
                        .invoiceEndDate(LocalDate.of(2022, 9, 30))
                        .consumption(BigDecimal.valueOf(272))
                        .c1(BigDecimal.valueOf(12)).c2(BigDecimal.valueOf(127)).c3(BigDecimal.valueOf(133))
                        .amount(BigDecimal.valueOf(49.06)).currency("euro")
                        .invoiceDue(LocalDate.of(2022, 9, 15)).invoicePaid(false)
                        .subscription(subscriptions[1])
                        .build(),
                Invoice.builder().invoiceStartDate(LocalDate.of(2022, 10, 1))
                        .invoiceEndDate(LocalDate.of(2022, 10, 30))
                        .consumption(BigDecimal.valueOf(255))
                        .c1(BigDecimal.valueOf(10)).c2(BigDecimal.valueOf(123)).c3(BigDecimal.valueOf(122))
                        .amount(BigDecimal.valueOf(46.59)).currency("euro")
                        .invoiceDue(LocalDate.of(2022, 10, 15)).invoicePaid(false)
                        .subscription(subscriptions[1])
                        .build(),
                Invoice.builder().invoiceStartDate(LocalDate.of(2022, 11, 1))
                        .invoiceEndDate(LocalDate.of(2022, 11, 30))
                        .consumption(BigDecimal.valueOf(203))
                        .c1(BigDecimal.valueOf(14)).c2(BigDecimal.valueOf(131)).c3(BigDecimal.valueOf(58))
                        .amount(BigDecimal.valueOf(39.04)).currency("euro")
                        .invoiceDue(LocalDate.of(2022, 11, 15)).invoicePaid(false)
                        .subscription(subscriptions[1])
                        .build(),
                Invoice.builder().invoiceStartDate(LocalDate.of(2022, 12, 1))
                        .invoiceEndDate(LocalDate.of(2022, 12, 30))
                        .consumption(BigDecimal.valueOf(194))
                        .c1(BigDecimal.valueOf(14)).c2(BigDecimal.valueOf(131)).c3(BigDecimal.valueOf(45))
                        .amount(BigDecimal.valueOf(37.73)).currency("euro")
                        .invoiceDue(LocalDate.of(2022, 12, 15)).invoicePaid(false)
                        .subscription(subscriptions[1])
                        .build(),
                Invoice.builder().invoiceStartDate(LocalDate.of(2023, 1, 1))
                        .invoiceEndDate(LocalDate.of(2023, 1, 30))
                        .consumption(BigDecimal.valueOf(312))
                        .c1(BigDecimal.valueOf(13)).c2(BigDecimal.valueOf(131)).c3(BigDecimal.valueOf(168))
                        .amount(BigDecimal.valueOf(54.87)).currency("euro")
                        .invoiceDue(LocalDate.of(2023, 1, 15)).invoicePaid(false)
                        .subscription(subscriptions[1])
                        .build(),
                Invoice.builder().invoiceStartDate(LocalDate.of(2023, 2, 1))
                        .invoiceEndDate(LocalDate.of(2023, 2, 28))
                        .consumption(BigDecimal.valueOf(221))
                        .c1(BigDecimal.valueOf(11)).c2(BigDecimal.valueOf(126)).c3(BigDecimal.valueOf(84))
                        .amount(BigDecimal.valueOf(41.65)).currency("euro")
                        .invoiceDue(LocalDate.of(2023, 2, 15)).invoicePaid(false)
                        .subscription(subscriptions[1])
                        .build(),
                Invoice.builder().invoiceStartDate(LocalDate.of(2023, 3, 1))
                        .invoiceEndDate(LocalDate.of(2023, 3, 30))
                        .consumption(BigDecimal.valueOf(255))
                        .c1(BigDecimal.valueOf(12)).c2(BigDecimal.valueOf(127)).c3(BigDecimal.valueOf(116))
                        .amount(BigDecimal.valueOf(46.59)).currency("euro")
                        .invoiceDue(LocalDate.of(2023, 3, 15)).invoicePaid(false)
                        .subscription(subscriptions[1])
                        .build(),
                Invoice.builder().invoiceStartDate(LocalDate.of(2023, 4, 1))
                        .invoiceEndDate(LocalDate.of(2023, 4, 30))
                        .consumption(BigDecimal.valueOf(314))
                        .c1(BigDecimal.valueOf(13)).c2(BigDecimal.valueOf(131)).c3(BigDecimal.valueOf(170))
                        .amount(BigDecimal.valueOf(55.17)).currency("euro")
                        .invoiceDue(LocalDate.of(2023, 4, 15)).invoicePaid(false)
                        .subscription(subscriptions[1])
                        .build(),
                Invoice.builder().invoiceStartDate(LocalDate.of(2023, 5, 1))
                        .invoiceEndDate(LocalDate.of(2023, 5, 30))
                        .consumption(BigDecimal.valueOf(375))
                        .c1(BigDecimal.valueOf(11)).c2(BigDecimal.valueOf(126)).c3(BigDecimal.valueOf(239))
                        .amount(BigDecimal.valueOf(64.18)).currency("euro")
                        .invoiceDue(LocalDate.of(2023, 5, 15)).invoicePaid(false)
                        .subscription(subscriptions[1])
                        .build(),
                Invoice.builder().invoiceStartDate(LocalDate.of(2023, 6, 1))
                        .invoiceEndDate(LocalDate.of(2023, 6, 30))
                        .consumption(BigDecimal.valueOf(288))
                        .c1(BigDecimal.valueOf(12)).c2(BigDecimal.valueOf(127)).c3(BigDecimal.valueOf(149))
                        .amount(BigDecimal.valueOf(51.39)).currency("euro")
                        .invoiceDue(LocalDate.of(2023, 6, 15)).invoicePaid(false)
                        .subscription(subscriptions[1])
                        .build(),
                Invoice.builder().invoiceStartDate(LocalDate.of(2023, 1, 1))
                        .invoiceEndDate(LocalDate.of(2023, 1, 30))
                        .consumption(BigDecimal.valueOf(270))
                        .c1(BigDecimal.valueOf(12)).c2(BigDecimal.valueOf(120)).c3(BigDecimal.valueOf(138))
                        .amount(BigDecimal.valueOf(52.87)).currency("euro")
                        .invoiceDue(LocalDate.of(2023, 1, 15)).invoicePaid(false)
                        .subscription(subscriptions[2])
                        .build(),
                Invoice.builder().invoiceStartDate(LocalDate.of(2023, 2, 1))
                        .invoiceEndDate(LocalDate.of(2023, 2, 28))
                        .consumption(BigDecimal.valueOf(266))
                        .c1(BigDecimal.valueOf(11)).c2(BigDecimal.valueOf(125)).c3(BigDecimal.valueOf(130))
                        .amount(BigDecimal.valueOf(52.24)).currency("euro")
                        .invoiceDue(LocalDate.of(2023, 2, 15)).invoicePaid(true)
                        .subscription(subscriptions[2])
                        .build(),
        };
        this.invoiceRepository.saveAll(Arrays.asList(invoices));
        LogManager.getLogger(this.getClass()).warn("        ------- invoices");

    }

}
