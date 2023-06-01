package com.tfm.backend.api.resources;

import com.tfm.backend.api.dtos.InvoiceDto;
import com.tfm.backend.services.InvoiceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping(InvoiceResource.INVOICE)
public class InvoiceResource {
    public static final String INVOICE = "/invoice";
    public static final String ID_ID = "/{id}";
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

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = ID_ID , produces = {"application/pdf", "application/json"})
    public ResponseEntity<byte[]> readReceipt(@PathVariable String id) {
        return this.invoiceService.createInvoice(Integer.parseInt(id));
    }

}
