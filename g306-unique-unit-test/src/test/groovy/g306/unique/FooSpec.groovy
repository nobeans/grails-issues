package g306.unique

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Foo)
class FooSpec extends Specification {

    void "Foo's name should be unique"() {
        given:
        def foo1 = new Foo(name: "FOO1")
        def bar = new Bar(name: "BAR1")
        foo1.bar = bar
        foo1.save()
        assert Foo.count() == 1

        and:
        def foo2 = new Foo(name: "FOO1")
        foo2.bar = new Bar(name: "BAR2")

        when:
        foo2.save()

        then:
        foo2.hasErrors()
        foo2.errors['name']?.code == 'unique'
    }

    void "Foo's bar should be unique, but..."() {
        given:
        def foo1 = new Foo(name: "FOO1")
        def bar = new Bar(name: "BAR")
        foo1.bar = bar
        foo1.save()
        assert Foo.count() == 1

        and:
        def foo2 = new Foo(name: "FOO2")
        foo2.bar = bar // using same Bar instance

        when:
        foo2.save()

        then:
        //foo2.hasErrors()
        foo2.errors['bar']?.code == 'unique'
    }
}
