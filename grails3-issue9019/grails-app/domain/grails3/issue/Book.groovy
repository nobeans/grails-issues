package grails3.issue

class Book {

    String title

    int countBeforeValidate
    int countBeforeInsert
    int countAfterInsert
    int countBeforeUpdate
    int countAfterUpdate

    static transients = ['countBeforeValidate', 'countBeforeInsert', 'countAfterInsert', 'countBeforeUpdate', 'countAfterUpdate']

    def beforeValidate() {
        printCounters("beforeValidate")
        countBeforeValidate++
    }
    def beforeInsert() {
        printCounters("beforeInsert")
        countBeforeInsert++
    }
    def afterInsert() {
        printCounters("afterInsert")
        countAfterInsert++
    }
    def beforeUpdate() {
        printCounters("beforeUpdate")
        countBeforeUpdate++
    }
    def afterUpdate() {
        printCounters("afterUpdate")
        countAfterUpdate++
    }

    static constraints = {
    }

    private printCounters(label) {
        println ">"*50
        println label
        println "-"*10
        println countBeforeValidate
        println countBeforeValidate
        println countBeforeInsert
        println countAfterInsert
        println countBeforeUpdate
        println countAfterUpdate
        println "<"*50
        println countBeforeValidate
    }
}
