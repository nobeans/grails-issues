package g330rc1.passwordencryptioncodec

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.springframework.context.EnvironmentAware
import org.springframework.core.env.Environment
import org.springframework.core.env.MapPropertySource

import java.nio.charset.StandardCharsets

class Application extends GrailsAutoConfiguration implements EnvironmentAware {

    @Override
    void setEnvironment(Environment environment) {
        File appConfigFile = new File("conf/external-config.groovy")
        if (appConfigFile.exists()) {
            ConfigObject config = new ConfigSlurper().parse(appConfigFile.getText(StandardCharsets.UTF_8.name()))
            environment.propertySources.addFirst(new MapPropertySource("external-config", config))
        }
    }

    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }
}
