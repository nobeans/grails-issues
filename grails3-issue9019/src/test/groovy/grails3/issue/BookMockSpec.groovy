package grails3.issue

import grails.test.mixin.Mock
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
@Mock(Book)
class BookMockSpec extends Specification {

    def book

    def setup() {
        book = new Book(title: "Test")
        println "=" * 100
    }

    void "validate"() {
        when:
        book.validate()

        then:
        book.countBeforeValidate == 1
        book.countBeforeInsert == 0
        book.countAfterInsert == 0
        book.countBeforeUpdate == 0
        book.countAfterUpdate == 0
    }

    void "save as create"() {
        when:
        book.save(flush: true)

        then:
        book.countBeforeValidate == 1
        book.countBeforeInsert == 9
        book.countAfterInsert == 9
        book.countBeforeUpdate == 0
        book.countAfterUpdate == 0
    }

    void "save as update"() {
        given:
        book.save(flush: true)
        resetCounter(book)

        when:
        book.save(flush: true)

        then:
        book.countBeforeValidate == 1
        book.countBeforeInsert == 0
        book.countAfterInsert == 0
        book.countBeforeUpdate == 13
        book.countAfterUpdate == 13
    }

    void "save as create without validate"() {
        when:
        book.save(flush: true, validate: false)

        then:
        book.countBeforeValidate == 0
        book.countBeforeInsert == 17
        book.countAfterInsert == 17
        book.countBeforeUpdate == 0
        book.countAfterUpdate == 0
    }

    void "save as update without validate"() {
        given:
        book.save(flush: true)
        resetCounter(book)

        when:
        book.save(flush: true, validate: false)

        then:
        book.countBeforeValidate == 0
        book.countBeforeInsert == 0
        book.countAfterInsert == 0
        book.countBeforeUpdate == 21
        book.countAfterUpdate == 21
    }

    void "save as create without validate -- 2"() {
        when:
        book.save(flush: true, validate: false)

        then:
        book.countBeforeValidate == 0
        book.countBeforeInsert == 25 // Why!!??
        book.countAfterInsert == 25
        book.countBeforeUpdate == 0
        book.countAfterUpdate == 0
    }

    private resetCounter(Book book) {
        book.countBeforeValidate = 0
        book.countBeforeInsert = 0
        book.countAfterInsert = 0
        book.countBeforeUpdate = 0
        book.countAfterUpdate = 0
        assert book.countBeforeValidate == 0
        assert book.countBeforeInsert == 0
        assert book.countAfterInsert == 0
        assert book.countBeforeUpdate == 0
        assert book.countAfterUpdate == 0
    }
}
