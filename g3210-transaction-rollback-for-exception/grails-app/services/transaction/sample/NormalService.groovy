package transaction.sample

class NormalService {

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
