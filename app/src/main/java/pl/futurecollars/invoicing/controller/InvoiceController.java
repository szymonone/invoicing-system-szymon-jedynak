package pl.futurecollars.invoicing.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.futurecollars.invoicing.db.InMemoryDatabase;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.service.InvoiceService;

@RestController
@RequestMapping("invoice")
public class InvoiceController {

    private InvoiceService invoiceService = new InvoiceService(new InMemoryDatabase());

    @GetMapping
    public List<Invoice> getAll() {
        return invoiceService.getAlL();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Invoice>> getExactInvoice(@PathVariable("id") int id) {
        Optional<Invoice> optionalInvoice = invoiceService.getById(id);
        if (optionalInvoice.isPresent()) {
            return ResponseEntity.ok(optionalInvoice);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody Invoice invoice) {
        invoiceService.save(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Invoice>> delete(@PathVariable("id") int id) {
        Optional<Invoice> optionalInvoice = invoiceService.delete(id);
        if (optionalInvoice.isPresent()) {
            return ResponseEntity.ok(optionalInvoice);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Invoice>> update(@PathVariable int id, @RequestBody Invoice invoice) {
        Optional<Invoice> optionalInvoice = invoiceService.update(id, invoice);
        if (optionalInvoice.isPresent()) {
            return ResponseEntity.ok(optionalInvoice);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
