package transaction.sample

import grails.test.mixin.integration.Integration
import groovy.test.GroovyAssert
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
class GrailsTransactionAnnotationPojoSpec extends Specification implements TransactionTestableWithBook {

    // A declarative transaction REQUIRES using with DI.
    @Autowired
    GrailsTransactionAnnotationPojo pojo

    def "When RuntimeException is thrown from a transaction, the transaction should be rolled back"() {
        expect:
        withNewSessionAndNewTransaction {
            GroovyAssert.shouldFail(SampleRuntimeException) {
                pojo.throwRuntimeException()
            }

            assert Book.count() == 0
        }
    }

    def "When Error is thrown from a transaction, the transaction should be rolled back"() {
        expect:
        withNewSessionAndNewTransaction {
            GroovyAssert.shouldFail(Error) {
                pojo.throwError()
            }

            assert Book.count() == 0
        }
    }

    def "When Exception with a 'throws' clause is thrown from a transaction, the transaction should be committed"() {
        expect:
        withNewSessionAndNewTransaction {
            GroovyAssert.shouldFail(SampleException) {
                pojo.throwExceptionWithThrowsClause()
            }

            assert Book.count() == 1
        }
    }

    def "When Exception without a 'throws' clause is thrown from a transaction, the transaction should be committed"() {
        expect:
        withNewSessionAndNewTransaction {
            GroovyAssert.shouldFail(SampleException) {
                pojo.throwExceptionWithoutThrowsClause()
            }

            assert Book.count() == 1
        }
    }
}
