package pl.futurecollars.invoicing.db.file

import pl.futurecollars.invoicing.AbstractDatabaseTest
import pl.futurecollars.invoicing.db.Database
import pl.futurecollars.invoicing.service.FileService
import pl.futurecollars.invoicing.service.JsonService

import java.nio.file.Path

class FileDatabaseTest extends AbstractDatabaseTest {

    @Override
    Database getDatabaseInstance() {

        JsonService jsonService = new JsonService()
        FileService fileService = new FileService()
        Path pathToId = File.createTempFile('TemporaryId', '.txt').toPath()
        Path pathToInvoices = File.createTempFile('invoices', '.txt').toPath()
        IdCurrentNumber idService = new IdCurrentNumber(pathToId, fileService)

        return new FileDatabase(fileService, jsonService, idService, pathToInvoices)
    }
}
