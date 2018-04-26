package sample

class BootStrap {

    def init = { servletContext ->
        new Book(title: "Sample Book", author: "John").save(flush: true, failOnError: true)
    }
}
