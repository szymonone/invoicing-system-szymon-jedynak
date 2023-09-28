package pl.futurecollars.invoicing

import pl.futurecollars.invoicing.TestHelper
import pl.futurecollars.invoicing.db.Database
import spock.lang.Specification

abstract class AbstractDatabaseTest extends Specification {

    Database database = getDatabaseInstance()

    abstract Database getDatabaseInstance()

    def setup() {
        database.save(TestHelper.invoice(1))
        database.save(TestHelper.invoice(2))
        database.save(TestHelper.invoice(3))
        database.save(TestHelper.invoice(5))
    }

    def "Should save Invoice"() {
        expect:
        database.getById(2) == Optional.ofNullable(TestHelper.invoice(2))
    }

    def "Should get Invoice by ID"() {
        expect:
        database.getById(2) == Optional.ofNullable(TestHelper.invoice(2))
    }

    def "Should get all"() {
        expect:
        !database.getAlL().isEmpty()
    }

    def "Should update Invoice in data base"() {
        when:
        database.update(1, TestHelper.invoice(4))

        then:
        database.getById(1).isPresent()
    }

    def "shouldDeleteInvoice"() {
        when:
        database.delete(1)

        then:
        !database.getById(1).isPresent()
    }

    def "Should throw illegal argument exception for Update method"() {
        when:
        database.update(-2, TestHelper.invoice(4))

        then:
        thrown(IllegalArgumentException)
    }

    def "Should throw illegal argument exception for Delete method"() {
        when:
        database.delete(-2)

        then:
        thrown(IllegalArgumentException)
    }
}
