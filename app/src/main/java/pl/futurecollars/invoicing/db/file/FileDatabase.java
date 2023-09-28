package pl.futurecollars.invoicing.db.file;

import static pl.futurecollars.invoicing.configuration.Configuration.DATABASE_LOCATION;
import static pl.futurecollars.invoicing.configuration.Configuration.ID_FILE_LOCATION;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.service.FileService;
import pl.futurecollars.invoicing.service.JsonService;

@Data
@AllArgsConstructor
public class FileDatabase implements Database {

    private final FileService fileService;
    private final JsonService jsonService;
    private final IdCurrentNumber idCurrentNumber;

    @Override
    public int save(Invoice invoice) {
        invoice.setId(idCurrentNumber.getNextIdAndIncrement(ID_FILE_LOCATION));
        fileService.appendLineToFile(DATABASE_LOCATION, jsonService.objectToJson(invoice));
        return invoice.getId();
    }

    @Override
    public Optional<Invoice> getById(int id) {
        printIllegalArgumentException(id);
        printOutOfBoundsException(id);
        return fileService.readAllLines(DATABASE_LOCATION).stream()
                .filter(line -> line.contains("\"id\":" + id + ","))
                .map(jsonService::jsonToObject)
                .findFirst();
    }

    @Override
    public List<Invoice> getAlL() {
        return fileService.readAllLines(DATABASE_LOCATION).stream()
                .map(jsonService::jsonToObject)
                .toList();
    }

    @Override
    public void update(int id, Invoice updatedInvoice) {
        printIllegalArgumentException(id);
        printOutOfBoundsException(id);
        List<String> invoicesToUpdate = new ArrayList<>(fileService.readAllLines(DATABASE_LOCATION).stream()
                .filter(line -> !line.contains("\"id\":" + id + ","))
                .toList());

        updatedInvoice.setId(id);
        invoicesToUpdate.add(jsonService.objectToJson(updatedInvoice));
        fileService.writeLinesToFile(DATABASE_LOCATION, invoicesToUpdate);
    }

    @Override
    public void delete(int id) {
        printIllegalArgumentException(id);
        printOutOfBoundsException(id);
        List<String> invoicesToUpdate = new ArrayList<>(fileService.readAllLines(DATABASE_LOCATION).stream()
                .filter(line -> !line.contains("\"id\":" + id + ","))
                .toList());

        fileService.writeLinesToFile(DATABASE_LOCATION, invoicesToUpdate);
    }

    private void printIllegalArgumentException(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Error: id cannot be negative");
        }
    }

    private void printOutOfBoundsException(int id) {
        if (id > fileService.readAllLines(DATABASE_LOCATION).size()) {
            throw new IndexOutOfBoundsException("Error: id doesn't exist");
        }
    }
}
