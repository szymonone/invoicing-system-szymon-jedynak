package pl.futurecollars.invoicing.db.file;

import java.nio.file.Path;
import java.util.List;
import pl.futurecollars.invoicing.service.FileService;

public class IdCurrentNumber {

    private final FileService fileService;
    private final Path idFilePath;
    private int nextId = 1;

    public IdCurrentNumber(Path idFilePath, FileService fileService) {
        this.idFilePath = idFilePath;
        this.fileService = fileService;

        List<String> lines = fileService.readAllLines(idFilePath);
        if (lines.isEmpty()) {
            this.fileService.writeTextTo(idFilePath, "1");
        } else {
            nextId = Integer.parseInt(lines.get(0));
        }
    }

    public int getNextIdAndIncrement() {
        fileService.writeTextTo(idFilePath, String.valueOf(nextId + 1));
        return nextId++;
    }
}
