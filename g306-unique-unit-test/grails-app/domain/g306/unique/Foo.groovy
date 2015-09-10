package g306.unique

class Foo {

    String name
    static hasOne = [bar: Bar]

    static constraints = {
        name unique: true
        bar unique: true
    }
}
