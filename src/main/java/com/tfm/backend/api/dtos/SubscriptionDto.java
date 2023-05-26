package com.tfm.backend.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tfm.backend.data.entities.Subscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionDto {
    private int id;
    private String planName;
    private String address;
    private Integer status;
    private String productName;
    private String cups;
    private String tariff;

    public SubscriptionDto(Subscription subscription) {
        BeanUtils.copyProperties(subscription, this);
        this.planName = subscription.getPlan().getName();
        this.address = subscription.getAddress();
        this.productName = subscription.getPlan().getProduct().getType();
        this.cups = subscription.getCups();
        this.tariff = subscription.getPlan().getProduct().getTariff().getName();
        if (subscription.getSubscription_end_date().isAfter(LocalDate.now()))
        {this.status = 1;} else {this.status = 0;}
    }
}
