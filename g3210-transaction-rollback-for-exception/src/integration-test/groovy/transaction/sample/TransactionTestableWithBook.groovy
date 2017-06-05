package transaction.sample

import org.junit.After
import org.junit.Before

trait TransactionTestableWithBook {

    @Before
    void checkNoBook() {
        withNewSessionAndNewTransaction {
            assert Book.count() == 0
        }
    }

    @After
    void cleanupBook() {
        withNewSessionAndNewTransaction {
            Book.withNewTransaction {
                println Book.list()
                Book.list()*.delete()
            }
        }
    }

    static void withNewSessionAndNewTransaction(Closure closure) {
        Book.withNewSession {
            Book.withNewTransaction {
                closure.call()
            }
        }
    }
}
