package g318.databinding.issue

import grails.databinding.DataBinder
import grails.databinding.SimpleMapDataBindingSource
import grails.test.mixin.TestFor
import grails.web.databinding.GrailsWebDataBinder
import groovy.util.logging.Slf4j
import spock.lang.Specification

@Slf4j
@TestFor(Sample)
class SampleSpec extends Specification {

    Sample sample
    DataBinder dataBinder

    def setup() {
        sample = new Sample()
        dataBinder = new GrailsWebDataBinder(grailsApplication)
    }

    void "A date property cannot clear to null when convertEmptyStringsToNull is false"() {
        given:
        dataBinder.convertEmptyStringsToNull = false
        dataBinder.trimStrings = true

        and:
        dataBinder.bind(sample, [text: "TEXT", number: 99, bool: true, myDate: new Date(), myDate2: new Date()] as SimpleMapDataBindingSource)
        sample.save(failOnError: true, flush: true)
        log.info sample.dump()

        when:
        dataBinder.bind(sample, [text: "", number: "", bool: "", myDate: "", myDate2: ""] as SimpleMapDataBindingSource)
        log.info sample.dump()
        sample.save(flush: true)

        then:
        log.info sample.dump()
        !sample.hasErrors()

        and:
        def sample2 = Sample.get(sample.id)
        log.info sample2.dump()
        sample2.text == ""
        sample2.number == null
        sample2.bool == null
        sample2.myDate == null
        sample2.myDate2 == null

        when:
        dataBinder.bind(sample, [text: null, number: null, bool: null, myDate: null, myDate2: null] as SimpleMapDataBindingSource)
        log.info sample.dump()
        sample.save(flush: true)

        then:
        log.info sample.dump()
        !sample.hasErrors()

        and:
        def sample3 = Sample.get(sample.id)
        log.info sample3.dump()
        sample3.text == null
        sample3.number == null
        sample3.bool == null
        sample3.myDate == null
        sample3.myDate2 == null
    }

    void "All properties can clear to null when convertEmptyStringsToNull is true"() {
        given:
        dataBinder.convertEmptyStringsToNull = true
        dataBinder.trimStrings = true

        and:
        dataBinder.bind(sample, [text: "TEXT", number: 99, bool: true, myDate: new Date(), myDate2: new Date()] as SimpleMapDataBindingSource)
        sample.save(failOnError: true, flush: true)
        log.info sample.dump()

        when:
        dataBinder.bind(sample, [text: "", number: "", bool: "", myDate: "", myDate2: ""] as SimpleMapDataBindingSource)
        log.info sample.dump()
        sample.save(flush: true)

        then:
        log.info sample.dump()
        !sample.hasErrors()

        and:
        def sample2 = Sample.get(sample.id)
        log.info sample2.dump()
        sample2.text == null
        sample2.number == null
        sample2.bool == null
        sample2.myDate == null
        sample2.myDate2 == null

        when:
        dataBinder.bind(sample, [text: null, number: null, bool: null, myDate: null, myDate2: null] as SimpleMapDataBindingSource)
        log.info sample.dump()
        sample.save(flush: true)

        then:
        log.info sample.dump()
        !sample.hasErrors()

        and:
        def sample3 = Sample.get(sample.id)
        log.info sample3.dump()
        sample3.text == null
        sample3.number == null
        sample3.bool == null
        sample3.myDate == null
        sample3.myDate2 == null
    }
}
