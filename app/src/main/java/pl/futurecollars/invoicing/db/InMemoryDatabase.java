package pl.futurecollars.invoicing.db;

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
        invoices.put(nextId, invoice);
        invoice.setId(nextId);
        return nextId++;
    }

    @Override
    public Optional<Invoice> getById(int id) {
        printIllegalArgumentException(id);
        printOutOfBoundsException(id);
        return invoices.values()
                .stream()
                .filter(p -> p == invoices.get(id))
                .findAny();
    }

    @Override
    public List<Invoice> getAlL() {
        return invoices.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Invoice> update(int id, Invoice updatedInvoice) {
        printIllegalArgumentException(id);
        printOutOfBoundsException(id);
        invoices.replace(id, invoices.get(id), updatedInvoice);
        updatedInvoice.setId(id);
        return Optional.of(updatedInvoice);
    }

    @Override
    public Optional<Invoice> delete(int id) {
        printIllegalArgumentException(id);
        printOutOfBoundsException(id);
        return Optional.ofNullable(invoices.remove(id));
    }

    private void printIllegalArgumentException(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Error: id cannot be negative");
        }
    }

    private void printOutOfBoundsException(int id) {
        if (id > nextId) {
            throw new IndexOutOfBoundsException("Error: id doesn't exist");
        }
    }
}
