package pl.futurecollars.invoicing.db.files

import pl.futurecollars.invoicing.db.file.IdCurrentNumber
import pl.futurecollars.invoicing.service.FileService
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path

class IdCurrentNumberTest extends Specification {

    FileService filesService = new FileService()
    private Path path = File.createTempFile('TemporaryId', '.txt').toPath()

    def "shouldGetNextIdAndIncrement"() {
        given:
        Files.writeString(path as Path, "3")
        IdCurrentNumber idService = new IdCurrentNumber(filesService, path as String)

        expect:
        idService.getNextIdAndIncrement(path as String)
        ['4'] == Files.readAllLines(path)
    }
}
