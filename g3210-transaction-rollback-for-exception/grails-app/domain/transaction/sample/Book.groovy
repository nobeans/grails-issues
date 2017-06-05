package transaction.sample

class Book {

    String title

    static constraints = {
        title blank: false, maxSize: 100
    }
}
