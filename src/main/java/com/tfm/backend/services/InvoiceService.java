package com.tfm.backend.services;

import com.tfm.backend.data.daos.InvoiceRepository;
import com.tfm.backend.data.entities.Invoice;
import com.tfm.backend.data.entities.Subscription;
import com.tfm.backend.services.utils.PdfTicketBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Stream<Invoice> findBySubscriptionId(Integer id) {
        return this.invoiceRepository.findBySubscriptionId(id).stream();
    }

    public ResponseEntity<byte[]> createInvoice(Integer id) {
        return this.invoiceRepository.findById(id)
                .map(invoice -> {
                    Subscription subscription = invoice.getSubscription();
                    return new PdfTicketBuilder().generateInvoice(invoice, subscription);
                })
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));
    }

}
