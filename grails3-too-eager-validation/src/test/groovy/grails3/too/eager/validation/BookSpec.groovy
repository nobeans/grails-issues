package grails3.too.eager.validation

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Book)
class BookSpec extends Specification {

    void "typeMismatch and validation error"() {
        given:
        def book = new Book(title: null, numberInShelf: "NOT_INTEGER")

        expect:
        book.hasErrors()
        book.errors.errorCount == 1
        book.errors['numberInShelf']?.code == 'typeMismatch'

        and:
        !book.validate()
        book.hasErrors()
        book.errors.errorCount == 2
        book.errors['title']?.code == 'nullable'
        book.errors['numberInShelf']?.code == 'typeMismatch'
    }
}
