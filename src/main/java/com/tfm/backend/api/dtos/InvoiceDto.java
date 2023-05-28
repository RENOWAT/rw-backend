package com.tfm.backend.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tfm.backend.data.entities.Invoice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDto {
    private Integer id;
    private BigDecimal consumption;
    private BigDecimal amount;
    private Integer year;
    private Integer month;
    private String status;

    public InvoiceDto(Invoice invoice) {
        BeanUtils.copyProperties(invoice, this);
        this.year = invoice.getInvoiceStartDate().getYear();
        this.month = invoice.getInvoiceStartDate().getMonthValue();
        this.status = invoice.getInvoicePaid().equals(true) ? "pagado" : "pendiente";
    }
}
