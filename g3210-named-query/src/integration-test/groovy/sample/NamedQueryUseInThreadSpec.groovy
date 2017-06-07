package sample

import grails.test.mixin.integration.Integration
import org.slf4j.LoggerFactory
import spock.lang.Specification

import java.lang.Thread.UncaughtExceptionHandler

@Integration
class NamedQueryUseInThreadSpec extends Specification {

    def setup() {
        Book.withNewTransaction {
            5.times {
                new Book(title: "BOOK_$it", datePublished: new Date()).save(failOnError: true, flush: true)
            }
            assert Book.count() == 5
        }
    }

    def cleanup() {
        Book.withNewTransaction {
            Book.list()*.delete()
        }
    }

    void "named query should be used as thread-safe"() {
        given:
        def failures = []

        when:
        def threads = (0..4).collect { int id ->
            Thread.start("THREAD_$id") {
                Book.withNewTransaction {
                    100.times {
                        assert Book.titleOf("BOOK_$id").count() == 1
                    }
                }
            }.each {
                it.uncaughtExceptionHandler = { Thread t, Throwable e ->
                    LoggerFactory.getLogger("TEST").error "Unexpected failure in ${t.name}", e
                    failures << "$id:${e.message}"
                } as UncaughtExceptionHandler
            }
        }

        and:
        threads*.join()

        then:
        failures.empty
    }
}
