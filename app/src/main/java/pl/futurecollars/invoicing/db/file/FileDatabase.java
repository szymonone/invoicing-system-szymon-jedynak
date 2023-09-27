package pl.futurecollars.invoicing.db.file;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.service.FileService;
import pl.futurecollars.invoicing.service.JsonService;

@AllArgsConstructor
public class FileDatabase implements Database {

    private final FileService fileService;
    private final JsonService jsonService;
    private final IdCurrentNumber idCurrentNumber;
    private final Path path;

    @Override
    public int save(Invoice invoice) {
        invoice.setId(idCurrentNumber.getNextIdAndIncrement());
        fileService.appendLineToFile(path, jsonService.objectToJson(invoice));
        return invoice.getId();
    }

    @Override
    public Optional<Invoice> getById(int id) {
        printIllegalArgumentException(id);
        return fileService.readAllLines(path).stream()
                .filter(line -> line.contains("\"id\":" + id + ","))
                .map(jsonInvoice -> jsonService.jsonToObject(jsonInvoice, Invoice.class))
                .findFirst();
    }

    @Override
    public List<Invoice> getAlL() {
        return fileService.readAllLines(path).stream()
                .map(jsonInvoice -> jsonService.jsonToObject(jsonInvoice, Invoice.class))
                .toList();
    }

    @Override
    public Optional<Invoice> update(int id, Invoice updatedInvoice) {
        printIllegalArgumentException(id);
        List<String> allInvoices = fileService.readAllLines(path);
        List<String> invoicesToUpdate = new ArrayList<>(allInvoices.stream()
                .filter(line -> !line.contains("\"id\":" + id + ","))
                .toList());

        if (allInvoices.size() == invoicesToUpdate.size()) {
            throw new IllegalArgumentException("Id " + id + " does not exist");
        }

        updatedInvoice.setId(id);
        invoicesToUpdate.add(jsonService.objectToJson(updatedInvoice));
        fileService.writeLinesToFile(path, invoicesToUpdate);
        return Optional.of(updatedInvoice);
    }

    @Override
    public Optional<Invoice> delete(int id) {
        printIllegalArgumentException(id);
        List<String> invoicesToUpdate = new ArrayList<>(fileService.readAllLines(path).stream()
                .filter(line -> !line.contains("\"id\":" + id + ","))
                .toList());

        Optional<Invoice> deletedInvoice = fileService.readAllLines(path).stream()
                .filter(line -> line.contains("\"id\":" + id + ","))
                .map(jsonInvoice -> jsonService.jsonToObject(jsonInvoice, Invoice.class))
                .findAny();

        fileService.writeLinesToFile(path, invoicesToUpdate);
        return deletedInvoice;

    }

    private void printIllegalArgumentException(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Error: id cannot be negative");
        }
    }
}
