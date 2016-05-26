package grails3.authmethods.tomcat

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

class TextController {

    static allowedMethods = [getOnly: "GET", postOnly: "POST"]

    def getOnly() {
        log.info "getOnly start"
        render text: "getOnly succeeded"
        log.info "getOnly end"
    }

    def postOnly() {
        // This isn't called by GET method when the WAR file is deployed to Tomcat.
        // And a exception is thrown.
        log.info "postOnly start"
        render text: "postOnly succeeded"
        log.info "postOnly end"
    }

    def render405() {
        log.info "render405 start"
        // This works even when the WAR file is deployed to Tomcat.
        render status: 405
        log.info "render405 end"
    }
}
