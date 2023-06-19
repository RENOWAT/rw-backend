package com.tfm.backend.services;

import com.tfm.backend.TestConfig;
import com.tfm.backend.data.daos.InvoiceRepository;
import com.tfm.backend.data.entities.Invoice;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@TestConfig
public class InvoiceServiceIT {

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    @Test
    public void testFindBySubscriptionId() {
        Integer subscriptionId = 1;
        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(new Invoice());
        invoiceList.add(new Invoice());
        when(invoiceRepository.findBySubscriptionId(subscriptionId)).thenReturn(invoiceList);
        Stream<Invoice> result = invoiceService.findBySubscriptionId(subscriptionId);
        assertEquals(2, result.count());
    }

}
