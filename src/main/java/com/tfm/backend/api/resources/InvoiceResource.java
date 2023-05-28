package com.tfm.backend.api.resources;

import com.tfm.backend.api.dtos.InvoiceDto;
import com.tfm.backend.services.InvoiceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping(InvoiceResource.INVOICE)
public class InvoiceResource {
    public static final String INVOICE = "/invoice";
    private final InvoiceService invoiceService;

    @Autowired
    InvoiceResource(InvoiceService invoiceService) {this.invoiceService = invoiceService;}

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public Stream<InvoiceDto> findInvoicesBySubscriptionId(
            @RequestParam(required = false) Integer id) {
        return this.invoiceService.findBySubscriptionId(id)
                .map(InvoiceDto::new);
    }

}
