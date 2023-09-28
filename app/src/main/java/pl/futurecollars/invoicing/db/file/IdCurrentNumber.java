package pl.futurecollars.invoicing.db.file;

import java.util.List;
import pl.futurecollars.invoicing.service.FileService;

public class IdCurrentNumber {

    private final FileService fileService;
    private int nextId = 1;

    public IdCurrentNumber(FileService fileService, String path) {
        this.fileService = fileService;

        List<String> lines = fileService.readAllLines(path);
        if (lines.isEmpty()) {
            fileService.writeTextTo(path, "1");
        } else {
            nextId = Integer.parseInt(lines.get(0));
        }
    }

    public int getNextIdAndIncrement(String path) {
        fileService.writeTextTo(path, String.valueOf(nextId + 1));
        return nextId++;
    }
}
