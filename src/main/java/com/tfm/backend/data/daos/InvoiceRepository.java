package com.tfm.backend.data.daos;

import com.tfm.backend.data.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer > {
    List<Invoice> findByInvoiceStartDateGreaterThanEqual(LocalDate invoice_start_date);
    List<Invoice>  findBySubscriptionId(Integer id);
    Optional<Invoice> findById(Integer id);

}
