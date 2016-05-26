package grails3.authmethods.tomcat

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        //"404"(controller: 'errors', action: 'handle404')
        //"405"(controller: 'errors', action: 'handle405')
        //"500"(controller: 'errors', action: 'handle500')
        //"500"(view:'/error')
    }
}
