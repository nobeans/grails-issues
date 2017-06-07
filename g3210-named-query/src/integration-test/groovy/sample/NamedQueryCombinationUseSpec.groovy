package sample

import grails.test.mixin.integration.Integration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@Integration
@Transactional
class NamedQueryCombinationUseSpec extends Specification {

    Date theDay = new Date().clearTime()

    def setup() {
        5.times {
            new Book(title: "BOOK_$it", datePublished: theDay - it).save(failOnError: true, flush: true)
        }
        assert Book.count() == 5
    }

    void "single use of named query works well: titleOf"() {
        expect:
        Book.titleOf("BOOK_1").count() == 1
    }

    void "single use of named query works well: availableAt"() {
        expect:
        Book.availableAt(new Date(0)).count() == 0

        and:
        Book.availableAt(theDay - 1).count() == 4
    }

    // NOTE: If you comment out the two tests above, the two test below would be passed

    void "combination of named query doesn't work only after using them more than once"() {
        expect: // the second named-query is IGNORED!!!
        Book.titleOf("BOOK_1").availableAt(new Date(0)).count() == 0  //=> actual: 1 !!!!
    }

    void "combination of named query doesn't work only after using them more than once (2)"() {
        expect: // the second named-query is IGNORED!!!
        Book.availableAt(theDay - 1).titleOf("BOOK_1").count() == 1  //=> actual: 4 !!!!
    }
}
