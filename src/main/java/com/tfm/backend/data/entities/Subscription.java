package com.tfm.backend.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate subscription_start_date;
    private LocalDate subscription_end_date;
    private String address;
    private String cups;
    @OneToMany(mappedBy="subscription")
    @JsonIgnore
    private List<Invoice> invoices;
    @ManyToOne
    @JoinColumn(name="plan_id", nullable=false)
    private Plan plan;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;
}
