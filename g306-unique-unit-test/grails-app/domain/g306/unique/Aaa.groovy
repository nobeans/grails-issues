package g306.unique

class Aaa {

    String name
    Bbb bbb

    static constraints = {
        name unique: true
        bbb unique: true
    }
}
