package sample

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class BookController {

    BookService bookService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond bookService.list(params), model:[bookCount: bookService.count()]
    }

    def show(Long id) {
        // There are both show.gson and show.gsp.
        // The 'model' map's entry couldn't be used in gson.
        respond Book.get(id), model: [foo: "FOO"]
    }

    def show2(Long id) {
        // There is only show2.gson.
        // The all 'model' map's entry can be used in gson expectedly.
        render view: 'show2', model: [book: Book.get(id), foo: "FOO"]
    }

    def show3(Long id) {
        // There are both show3.gson and show3.gsp.
        // Even when I specified 'json' format, the response was HTML.
        render view: 'show3', model: [book: Book.get(id), foo: "FOO"]
    }

    def show4(Long id) {
        respond([view: 'show'], [book: Book.get(id), foo: "FOO"])
    }

    def show5(Long id) {
        Map model = [book:Book.get(id), foo: "FOO"]
        withFormat {
            '*' { render view: 'show5', model: model } // default=JSON
            html { render view: 'show5_', model: model }
        }
    }

    def show6(Long id) {
        Map model = [book:Book.get(id), foo: "FOO"]
        withFormat {
            '*' { render view: 'show6', model: model } // default=JSON
            html { render view: 'show6', model: model }
        }
    }

    def create() {
        respond new Book(params)
    }

    def save(Book book) {
        if (book == null) {
            notFound()
            return
        }

        try {
            bookService.save(book)
        } catch (ValidationException e) {
            respond book.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'book.label', default: 'Book'), book.id])
                redirect book
            }
            '*' { respond book, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond bookService.get(id)
    }

    def update(Book book) {
        if (book == null) {
            notFound()
            return
        }

        try {
            bookService.save(book)
        } catch (ValidationException e) {
            respond book.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'book.label', default: 'Book'), book.id])
                redirect book
            }
            '*'{ respond book, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        bookService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'book.label', default: 'Book'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
