package grails3.authmethods.tomcat

class ErrorsController {

    def handle404() {
        log.warn "Handle 404: method=${request.method}, request=${request["javax.servlet.error.request_uri"]}"
        render text: "404 in ErrorsController"
    }

    def handle405() {
        log.warn "Handle 405: method=${request.method}, request=${request["javax.servlet.error.request_uri"]}"
        render text: "405 in ErrorsController"
    }

    def handle500() {
        log.warn "Handle 500: method=${request.method}, request=${request["javax.servlet.error.request_uri"]}", request.exception
        render text: "500 in ErrorsController: exception=${request.exception}"
    }
}
