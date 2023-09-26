package pl.futurecollars.invoicing.service

import spock.lang.Specification
import java.nio.file.Path

class FileServiceTest extends Specification {

    FileService fileService = new FileService()
    List<String> invoicesExample = new ArrayList<>()
    Path path = File.createTempFile('TemporaryFile', '.txt').toPath()
    String line = ""

    def setup() {
        invoicesExample.add("Invoice1")
        invoicesExample.add("Invoice2")
    }

    def "Should write list to file"() {
        expect:
        fileService.appendLineToFile(path, line)
    }

    def "Should read invoices from file"() {
        expect:
        fileService.writeLinesToFile(path, invoicesExample)
    }

    def "Should write text to file"() {
        expect:
        fileService.writeTextTo(path, "Example String")
    }

    def "Should rEad text from file"() {
        given:
        fileService.writeTextTo(path, "Example String")

        expect:
        fileService.readAllLines(path) == (List.of("Example String"))
    }
}
