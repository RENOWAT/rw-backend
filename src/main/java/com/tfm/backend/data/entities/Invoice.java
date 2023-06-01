package com.tfm.backend.data.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate invoiceStartDate;
    private LocalDate invoiceEndDate;
    private BigDecimal consumption;
    private BigDecimal c1;
    private BigDecimal c2;
    private BigDecimal c3;
    private BigDecimal amount;
    private String currency;
    @CreationTimestamp
    private Date createdOn;
    private LocalDate invoiceDue;
    private Boolean invoicePaid;
    @ManyToOne
    @JoinColumn(name="subscription_id", nullable=false)
    private Subscription subscription;
}
