package transaction.sample

import grails.test.mixin.integration.Integration
import groovy.test.GroovyAssert
import spock.lang.Specification

@Integration
class ProgrammaticTransactionSpec extends Specification implements TransactionTestableWithBook {

    def "When RuntimeException is thrown from a transaction, the transaction should be rolled back"() {
        expect:
        withNewSessionAndNewTransaction {
            GroovyAssert.shouldFail(SampleRuntimeException) {
                Book.withNewTransaction {
                    new Book(title: "TEST").save(failOnError: true, flush: true)

                    throw new SampleRuntimeException("FOR TEST")
                }
            }

            assert Book.count() == 0
        }
    }

    def "When Error is thrown from a transaction, the transaction should be rolled back"() {
        expect:
        withNewSessionAndNewTransaction {
            GroovyAssert.shouldFail(Error) {
                Book.withNewTransaction {
                    new Book(title: "TEST").save(failOnError: true, flush: true)

                    throw new Error("FOR TEST")
                }
            }

            assert Book.count() == 0
        }
    }

    def "When Exception with a 'throws' clause is thrown from a transaction, the transaction should be committed"() {
        expect:
        withNewSessionAndNewTransaction {
            GroovyAssert.shouldFail(SampleException) {
                Book.withNewTransaction {
                    new Book(title: "TEST").save(failOnError: true, flush: true)

                    throw new SampleException("FOR TEST")
                }
            }

            assert Book.count() == 1
        }
    }

    def "When Exception without a 'throws' clause is thrown from a transaction, the transaction should be committed"() {
        expect:
        withNewSessionAndNewTransaction {
            GroovyAssert.shouldFail(SampleException) {
                Book.withNewTransaction {
                    new Book(title: "TEST").save(failOnError: true, flush: true)

                    throw new SampleException("FOR TEST")
                }
            }

            assert Book.count() == 1
        }
    }
}
