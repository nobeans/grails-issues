package grails3.sample

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Book)
class BookSpec extends Specification {

    void "listOrderBy doesn't work with order:desc"() {
        given:
        def books = (1..3).collect {
            new Book().save(flush: true)
        }

        expect:
        Book.list(sort: 'id', order: 'asc')*.id == [1, 2, 3]
        Book.list(sort: 'id', order: 'desc')*.id == [3, 2, 1]
        Book.listOrderById(order: 'asc')*.id == [1, 2, 3]
        Book.listOrderById(order: 'desc')*.id == [3, 2, 1] // FAILED!!
    }
}
