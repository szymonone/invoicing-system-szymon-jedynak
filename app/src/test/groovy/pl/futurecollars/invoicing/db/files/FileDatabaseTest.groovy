package pl.futurecollars.invoicing.db.files

import pl.futurecollars.invoicing.configuration.Configuration
import pl.futurecollars.invoicing.AbstractDatabaseTest
import pl.futurecollars.invoicing.db.Database
import pl.futurecollars.invoicing.db.file.FileDatabase
import pl.futurecollars.invoicing.db.file.IdCurrentNumber
import pl.futurecollars.invoicing.service.FileService
import pl.futurecollars.invoicing.service.JsonService

class FileDatabaseTest extends AbstractDatabaseTest {

    @Override
    Database getDatabaseInstance() {

        JsonService jsonService = new JsonService()
        FileService fileService = new FileService()
        IdCurrentNumber idCurrentNumber = new IdCurrentNumber(fileService, Configuration.ID_FILE_LOCATION)

        return new FileDatabase(fileService, jsonService, idCurrentNumber)
    }
}
