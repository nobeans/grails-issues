databaseChangeLog = {
    changeSet(author: "", id: "test-0001") {
        grailsChange {
            change {
                sql.execute "CREATE TABLE my_table (id SERIAL, version BIGINT, value TEXT)"
            }
            rollback {
                sql.execute "DROP TABLE my_table"
            }
        }
    }
}
