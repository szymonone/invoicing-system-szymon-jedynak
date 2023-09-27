package pl.futurecollars.invoicing.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.futurecollars.invoicing.db.file.IdCurrentNumber;
import pl.futurecollars.invoicing.db.files.FileDatabase;
import pl.futurecollars.invoicing.service.FileService;
import pl.futurecollars.invoicing.service.JsonService;

@Configuration
public class DatabaseConfiguration {

    private static final String DATABASE_LOCATION = "db";
    private static final String INVOICES_FILE_NAME = "invoices.txt";
    private static final String ID_FILE_NAME = "id.txt";

    @Bean
    public IdCurrentNumber idCurrentNumber(FileService filesService) throws IOException {
        Path idPath = Files.createTempFile(DATABASE_LOCATION, ID_FILE_NAME);
        return new IdCurrentNumber(idPath, filesService);
    }

    @Bean
    public Database fileDatabase(FileService filesService, JsonService jsonService, IdCurrentNumber idCurrentNumber) throws IOException {
        Path databasePath = Files.createTempFile(DATABASE_LOCATION, INVOICES_FILE_NAME);
        return new FileDatabase(filesService, jsonService, idCurrentNumber, databasePath);
    }

    @Bean
    public Database inMemoryDatabase() {
        return new InMemoryDatabase();
    }
}
