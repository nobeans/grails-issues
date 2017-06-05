package transaction.sample

import grails.transaction.Transactional
import org.springframework.transaction.annotation.Propagation

@Transactional(propagation = Propagation.REQUIRES_NEW)
class GrailsTransactionAnnotationPojo {

    void throwRuntimeException() {
        new Book(title: "TEST").save(failOnError: true, flush: true)

        throw new SampleRuntimeException("FOR TEST")
    }

    void throwError() {
        new Book(title: "TEST").save(failOnError: true, flush: true)

        throw new Error("FOR TEST")
    }

    void throwExceptionWithThrowsClause() throws SampleException {
        new Book(title: "TEST").save(failOnError: true, flush: true)

        throw new SampleException("FOR TEST")
    }

    void throwExceptionWithoutThrowsClause() {
        new Book(title: "TEST").save(failOnError: true, flush: true)

        throw new SampleException("FOR TEST")
    }
}
