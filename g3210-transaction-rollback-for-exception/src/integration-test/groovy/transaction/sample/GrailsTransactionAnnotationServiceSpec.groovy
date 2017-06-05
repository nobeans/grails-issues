package transaction.sample

import grails.test.mixin.integration.Integration
import groovy.test.GroovyAssert
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
class GrailsTransactionAnnotationServiceSpec extends Specification implements TransactionTestableWithBook {

    @Autowired
    GrailsTransactionAnnotationService service

    def "When RuntimeException is thrown from a transaction, the transaction should be rolled back"() {
        expect:
        withNewSessionAndNewTransaction {
            GroovyAssert.shouldFail(SampleRuntimeException) {
                service.throwRuntimeException()
            }

            assert Book.count() == 0
        }
    }

    def "When Error is thrown from a transaction, the transaction should be rolled back"() {
        expect:
        withNewSessionAndNewTransaction {
            GroovyAssert.shouldFail(Error) {
                service.throwError()
            }

            assert Book.count() == 0
        }
    }

    def "When Exception with a 'throws' clause is thrown from a transaction, the transaction should be committed"() {
        expect:
        withNewSessionAndNewTransaction {
            GroovyAssert.shouldFail(SampleException) {
                service.throwExceptionWithThrowsClause()
            }

            assert Book.count() == 1
        }
    }

    def "When Exception without a 'throws' clause is thrown from a transaction, the transaction should be committed"() {
        expect:
        withNewSessionAndNewTransaction {
            GroovyAssert.shouldFail(SampleException) {
                service.throwExceptionWithoutThrowsClause()
            }

            assert Book.count() == 1
        }
    }
}
