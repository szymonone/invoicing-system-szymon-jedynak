package pl.futurecollars.invoicing.service

import pl.futurecollars.invoicing.db.Database
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.service.InvoiceService
import spock.lang.Specification

class InvoiceServiceTest extends Specification {

    private Database database = Mock()
    private InvoiceService service = new InvoiceService(database);

    def "shouldSave"() {
        given:
        def invoice = new Invoice()

        when:
        service.save(invoice)

        then:
        1 * database.save(invoice)
    }

    def "shouldGetByID"() {
        when:
        service.getById(2)

        then:
        1 * database.getById(2)
    }

    def "shouldGetAll"() {
        when:
        service.getAlL()

        then:
        1 * database.getAlL()
    }

    def "shouldUpdate"() {
        given:
        def invoice = new Invoice()

        when:
        service.update(2, invoice)

        then:
        1 * database.update(2, invoice)
    }

    def "shouldDelete"() {
        when:
        service.delete(2)

        then:
        1 * database.delete(2)
    }
}
