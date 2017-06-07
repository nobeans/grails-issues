package sample

class Book {

    String title
    Date datePublished

    static namedQueries = {
        titleOf { String title ->
            eq 'title', title
        }

        availableAt { Date date ->
            le 'datePublished', date
        }
    }
}
