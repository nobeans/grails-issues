package grails3.authmethods.tomcat

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

class GspController {

    static allowedMethods = [getOnly: "GET", postOnly_renderView: "POST", postOnly_retunModel_existsView: "POST", postOnly_retunModel_notExistsView: "POST"]

    def getOnly() {
        log.info "getOnly start"
        render view: "index", model: [text: "getOnly succeeded"]
        log.info "getOnly end"
    }

    def postOnly_renderView() {
        log.info "postOnly_renderView start"
        render view: "index", model: [text: "postOnly_renderView succeeded"]
        log.info "postOnly_renderView end"
    }

    def postOnly_retunModel_existsView() {
        log.info "postOnly_retunModel_existsView start"
        try {
            return [text: "postOnly_retunModel_existsView succeeded"]
        } finally {
            log.info "postOnly_retunModel_existsView end"
        }
    }

    def postOnly_retunModel_notExistsView() {
        log.info "postOnly_retunModel_notExistsView start"
        try {
            return [text: "postOnly_retunModel_notExistsView succeeded"]
        } finally {
            log.info "postOnly_retunModel_notExistsView end"
        }
    }

    def render405_returnModel_existsView() {
        log.info "render405_returnModel_existsView start"
        render status: 405
        log.info "render405_returnModel_existsView end"
    }
}
