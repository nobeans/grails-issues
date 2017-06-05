package transaction.sample

import grails.test.mixin.integration.Integration
import groovy.test.GroovyAssert
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

import java.lang.reflect.UndeclaredThrowableException

@Integration
class SpringTransactionAnnotationPojoSpec extends Specification implements TransactionTestableWithBook {

    // A declarative transaction REQUIRES using with DI.
    @Autowired
    SpringTransactionAnnotationPojo pojo

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
            def e = GroovyAssert.shouldFail {
                pojo.throwExceptionWithoutThrowsClause()
            }

            // TODO When using Spring's annotation, The Exception without 'throws' clauses is wrapped by UndeclaredThrowableException.
            assert e instanceof UndeclaredThrowableException
            e.cause instanceof SampleException

            assert Book.count() == 1
        }
    }
}
