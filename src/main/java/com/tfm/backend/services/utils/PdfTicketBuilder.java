package com.tfm.backend.services.utils;

import com.tfm.backend.data.entities.Invoice;
import com.tfm.backend.data.entities.Subscription;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;


public class PdfTicketBuilder {

    private static final String[] TABLE_COLUMNS_HEADERS = {"Desc.", "Total", "Impuestos", "Total factura"};
    private static final float[] TABLE_COLUMNS_SIZES_TICKETS = {90, 15, 25, 35};
    private static final String PATH = "/pdfs/invoices/";
    private static final String FILE = "invoice-";

    public ResponseEntity<byte[]> generateInvoice(Invoice invoice, Subscription subscription) {

        PdfCoreBuilder pdf = new PdfCoreBuilder(PATH, FILE + invoice.getId());

        BigDecimal energyCost = invoice.getC1().multiply(subscription.getPlan().getP1())
                .add(invoice.getC2().multiply(subscription.getPlan().getP2()))
                .add(invoice.getC3().multiply(subscription.getPlan().getP3()))
                .setScale(3, RoundingMode.HALF_UP);
        BigDecimal energyCostC1 = invoice.getC1().multiply(subscription.getPlan().getP1()).setScale(3, RoundingMode.HALF_UP);
        BigDecimal energyCostC2 = invoice.getC2().multiply(subscription.getPlan().getP2()).setScale(3, RoundingMode.HALF_UP);
        BigDecimal energyCostC3 = invoice.getC3().multiply(subscription.getPlan().getP3()).setScale(3, RoundingMode.HALF_UP);
        BigDecimal fixedTerm = subscription.getPlan().getPtf().multiply(subscription.getPlan().getPtf()).setScale(3, RoundingMode.HALF_UP);
        BigDecimal totalCostBeforeTax = energyCost.add(fixedTerm).setScale(3, RoundingMode.HALF_UP);
        BigDecimal tax = totalCostBeforeTax.multiply(BigDecimal.valueOf(0.05)).setScale(3, RoundingMode.HALF_UP);

        pdf.head();
        pdf.paragraphEmphasized("Titular:"+ subscription.getCustomer().getUser().getFirstName()
                        + " "+ subscription.getCustomer().getUser().getFamilyName())
                .paragraphEmphasized("Dirección de suministro: "+ subscription.getCustomer().getUser().getAddress())
                .paragraphEmphasized(subscription.getPlan().getName())
                .paragraphEmphasized("Número de contrato: " + subscription.getId());

        pdf.barcode(String.valueOf(invoice.getId())).line();

        pdf.paragraphEmphasized("Detalle de la factura")
                .paragraph("Periodo: " + invoice.getInvoiceStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd  -  ")) +
                        invoice.getInvoiceEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .paragraph("Potencia facturada: " + fixedTerm+ " €")
                .paragraph("Energia facturada valle: " + invoice.getC1() + " kWh x "
                        + subscription.getPlan().getP1() + " €/kWh  - Total periodo valle " + energyCostC1 + " €")
                .paragraph("Energia facturada llano: " + invoice.getC2() + " kWh x "
                        + subscription.getPlan().getP2() + " €/kWh  - Total periodo llano " + energyCostC2 + " €")
                .paragraph("Energia facturada punta: " + invoice.getC3() + " kWh x "
                        + subscription.getPlan().getP3() + " €/kWh  - Total periodo punta " + energyCostC3 + " €")
                .paragraph("Total energía: " + energyCost + " €")
                .paragraph("Total antes de impuestos: " + totalCostBeforeTax + " €")
                .paragraph("IVA: " + tax + " €")
                .paragraphEmphasized("Total: " + invoice.getAmount()+ "€");

        PdfTableBuilder table = pdf.table(TABLE_COLUMNS_SIZES_TICKETS).tableColumnsHeader(TABLE_COLUMNS_HEADERS);
        table.tableCells(subscription.getPlan().getName(),totalCostBeforeTax + " €", tax + " €",
                invoice.getAmount().setScale(2, RoundingMode.HALF_UP) + "€").buildTable();
        //table.tableColspanRight(invoice.getAmount().setScale(2, RoundingMode.HALF_UP) + "€").buildTable();

        return new ResponseEntity<>(pdf.foot().build(), HttpStatus.OK);
    }

}
