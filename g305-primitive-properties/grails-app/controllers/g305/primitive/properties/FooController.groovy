package g305.primitive.properties

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FooController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Foo.list(params), model:[fooCount: Foo.count()]
    }

    def show(Foo foo) {
        respond foo
    }

    def create() {
        respond new Foo(params)
    }

    @Transactional
    def save(Foo foo) {
        if (foo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (foo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond foo.errors, view:'create'
            return
        }

        foo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'foo.label', default: 'Foo'), foo.id])
                redirect foo
            }
            '*' { respond foo, [status: CREATED] }
        }
    }

    def edit(Foo foo) {
        respond foo
    }

    @Transactional
    def update(Foo foo) {
        if (foo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (foo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond foo.errors, view:'edit'
            return
        }

        foo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'foo.label', default: 'Foo'), foo.id])
                redirect foo
            }
            '*'{ respond foo, [status: OK] }
        }
    }

    @Transactional
    def delete(Foo foo) {

        if (foo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        foo.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'foo.label', default: 'Foo'), foo.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'foo.label', default: 'Foo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
