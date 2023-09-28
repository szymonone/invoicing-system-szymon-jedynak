package pl.futurecollars.invoicing.db

import pl.futurecollars.invoicing.AbstractDatabaseTest

class InMemoryDatabaseTest extends AbstractDatabaseTest {

    @Override
    Database getDatabaseInstance() {
        return new InMemoryDatabase()
    }
}
