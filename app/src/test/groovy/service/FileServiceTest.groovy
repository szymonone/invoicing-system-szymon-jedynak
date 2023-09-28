package pl.futurecollars.invoicing.service

import spock.lang.Specification

class FileServiceTest extends Specification {

    FileService filesService = new FileService()
    List<String> invoicesExample = new ArrayList<>()
    String line = ""

    def setup() {
        invoicesExample.add("Invoice1")
        invoicesExample.add("Invoice2")
    }

    def "shouldWriteListToFile"() {
        expect:
        filesService.appendLineToFile("temporaryFileToAppendLine.txt", line)
    }

    def "Should read invoices from file"() {
        expect:
        filesService.writeLinesToFile("temporaryFileToWriteLines.txt", invoicesExample)
    }

    def "shouldWriteTextToFile"() {
        expect:
        filesService.writeTextTo("temporaryFileToTest.txt", "Example String")
    }

    def "shouldREadTextFromFile"() {
        expect:
        filesService.readAllLines("temporaryFileToTest.txt") == (List.of("Example String"))
    }
}
