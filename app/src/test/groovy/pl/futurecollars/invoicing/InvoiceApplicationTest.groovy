package pl.futurecollars.invoicing

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.futurecollars.invoicing.service.InvoiceService
import spock.lang.Specification

@SpringBootTest
class InvoiceApplicationTest extends Specification {

    @Autowired
    private InvoiceService invoiceService

    def "invoice service id created"() {
        expect:
        invoiceService
    }

    @Test
    void contextLoad(){
    }
}
