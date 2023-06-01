package com.tfm.backend.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plan")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Integer planNumber;
    @Column(nullable = false, columnDefinition = "numeric(19,4)")
    private BigDecimal p1;
    @Column(nullable = false, columnDefinition = "numeric(19,4)")
    private BigDecimal p2;
    @Column(nullable = false, columnDefinition = "numeric(19,4)")
    private BigDecimal p3;
    @Column(nullable = false)
    private BigDecimal tf;
    @Column(nullable = false, columnDefinition = "numeric(19,5)")
    private BigDecimal ptf;
    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;
    @OneToMany(mappedBy="plan")
    @JsonIgnore
    private List<Subscription> subscriptions;
}
