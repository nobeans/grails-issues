package g318.databinding.issue

import grails.databinding.*

class Sample {

    String text
    Integer number
    Boolean bool
    Date myDate

    @BindingFormat('yyyy-MM')
    Date myDate2

    static constraints = {
        text nullable: true
        number nullable: true
        bool nullable: true
        myDate nullable: true
        myDate2 nullable: true
    }
}
