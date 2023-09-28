package pl.futurecollars.invoicing.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import pl.futurecollars.invoicing.model.Invoice;

public class InMemoryDatabase implements Database {

    private final Map<Integer, Invoice> invoices = new HashMap<>();
    private int nextId = 1;

    @Override
    public int save(Invoice invoice) {
        invoice.setId(nextId);
        invoices.put(nextId, invoice);
        return nextId++;
    }

    @Override
    public Optional<Invoice> getById(int id) {
        printIllegalArgumentException(id);
        return Optional.ofNullable(invoices.get(id));
    }

    @Override
    public List<Invoice> getAlL() {
        return new ArrayList<>(invoices.values());
    }

    @Override
    public void update(int id, Invoice updatedInvoice) {
        if (!invoices.containsKey(id)) {
            throw new IllegalArgumentException("Id " + id + " does not exist");
        }
        updatedInvoice.setId(id);
        invoices.put(id, updatedInvoice);
    }

    @Override
    public void delete(int id) {
        if (!invoices.containsKey(id)) {
            throw new IllegalArgumentException("Id " + id + " does not exist");
        }
        invoices.remove(id);
    }

    private void printIllegalArgumentException(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Error: id cannot be negative");
        }
    }
}
