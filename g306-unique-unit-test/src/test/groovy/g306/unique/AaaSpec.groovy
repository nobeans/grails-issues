package g306.unique

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Aaa)
class AaaSpec extends Specification {

    void "Aaa's name should be unique"() {
        given:
        def aaa1 = new Aaa(name: "AAA1")
        def bbb = new Bbb(name: "BBB1")
        aaa1.bbb = bbb
        aaa1.save()
        assert Aaa.count() == 1

        and:
        def aaa2 = new Aaa(name: "AAA1")
        aaa2.bbb = new Bbb(name: "BBB2")

        when:
        aaa2.save()

        then:
        aaa2.hasErrors()
        aaa2.errors['name']?.code == 'unique'
    }

    void "Aaa's bbb should be unique"() {
        given:
        def aaa1 = new Aaa(name: "AAA1")
        def bbb = new Bbb(name: "BBB")
        aaa1.bbb = bbb
        aaa1.save()
        assert Aaa.count() == 1

        and:
        def aaa2 = new Aaa(name: "AAA2")
        aaa2.bbb = bbb // using same Bbb instance

        when:
        aaa2.save()

        then:
        aaa2.hasErrors()
        aaa2.errors['bbb']?.code == 'unique'
    }
}
