package grails3.issue

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Book)
class BookTestForSpec extends Specification {

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
        book.countBeforeInsert == 11
        book.countAfterInsert == 11
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
        book.countBeforeUpdate == 16
        book.countAfterUpdate == 16
    }

    void "save as create without validate"() {
        when:
        book.save(flush: true, validate: false)

        then:
        book.countBeforeValidate == 0
        book.countBeforeInsert == 21
        book.countAfterInsert == 21
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
        book.countBeforeUpdate == 26
        book.countAfterUpdate == 26
    }

    void "save as create without validate -- 2"() {
        when:
        book.save(flush: true, validate: false)

        then:
        book.countBeforeValidate == 0
        book.countBeforeInsert == 31 // Why!!??
        book.countAfterInsert == 31
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
