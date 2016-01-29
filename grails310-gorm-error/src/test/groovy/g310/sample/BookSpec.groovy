package g310.sample

import spock.lang.Specification
import grails.test.mixin.integration.*
import org.springframework.transaction.annotation.*

@Integration
@Transactional
class BookSpec extends Specification {

    void cleanup() {
        Book.list()*.delete()
        Author.list()*.delete()
    }

    void "cascading save"() {
        given:
        def author = new Author(name: "Mike")
        def book = new Book(title: "Hello World", author: author)

        when:
        book.save(flush: true, failOnError: true)

        then:
        Author.count() == 1
        Book.count() == 1
    }

    void "individually save"() {
        given:
        def author = new Author(name: "Mike").save(flush: true, failOnError: true)
        Author.count() == 1

        and:
        def book = new Book(title: "Hello World", author: author)

        when:
        book.save(flush: true, failOnError: true)

        then:
        Author.count() == 1
        Book.count() == 1
    }

    void "individually save in another session"() {
        given:
        def author = Author.withNewTransaction { new Author(name: "Mike").save(flush: true, failOnError: true) }
        Author.count() == 1

        and:
        def book = new Book(title: "Hello World", author: author)

        when:
        book.save(flush: true, failOnError: true)

        then:
        Author.count() == 1
        Book.count() == 1
    }
}
