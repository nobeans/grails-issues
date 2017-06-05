package transaction.sample

import grails.test.mixin.integration.Integration
import groovy.test.GroovyAssert
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
class NormalServiceSpec extends Specification implements TransactionTestableWithBook {

    @Autowired
    NormalService service

    def "With Grails 3.1 and above、there is no transaction without @Transactional (1)"() {
        expect:
        withNewSessionAndNewTransaction {
            GroovyAssert.shouldFail(SampleRuntimeException) {
                service.throwRuntimeException()
            }

            assert Book.count() == 1
        }
    }

    def "With Grails 3.1 and above、there is no transaction without @Transactional (2)"() {
        expect:
        withNewSessionAndNewTransaction {
            GroovyAssert.shouldFail(Error) {
                service.throwError()
            }

            assert Book.count() == 1
        }
    }

    def "With Grails 3.1 and above、there is no transaction without @Transactional (3)"() {
        expect:
        withNewSessionAndNewTransaction {
            GroovyAssert.shouldFail(SampleException) {
                service.throwExceptionWithThrowsClause()
            }

            assert Book.count() == 1
        }
    }

    def "With Grails 3.1 and above、there is no transaction without @Transactional (4)"() {
        expect:
        withNewSessionAndNewTransaction {
            GroovyAssert.shouldFail(SampleException) {
                service.throwExceptionWithoutThrowsClause()
            }

            assert Book.count() == 1
        }
    }
}
