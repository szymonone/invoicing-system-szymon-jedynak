package pl.futurecollars.invoicing

import pl.futurecollars.invoicing.model.Company
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.InvoiceEntry
import pl.futurecollars.invoicing.model.Vat

import java.time.LocalDate

class TestHelper {

    static company(int id) {
        new Company("Name $id", "ul. Olchowa 24d/7 02-703 Warszawa, Polska", "iCode Trust Sp. z o.o")
    }

    static product(int id) {
        new InvoiceEntry("Programming course $id", BigDecimal.valueOf(id * 1000), BigDecimal.valueOf(id * 1000 * 0.08), Vat.VAT_8)
    }

    static invoice(int id) {
//        new Invoice(LocalDate.now(), company(id), company(id), List.of(product(id)))
        new Invoice(id, LocalDate.now(), company(id), company(id), List.of(product(id)))
    }


}
