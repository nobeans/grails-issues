import geb.spock.GebReportingSpec

import spock.lang.*

class SampleSpec extends GebReportingSpec {

    def "Google"() {
        when:
        go "http://www.google.com"

        then:
        title == "Google"
    }
}
