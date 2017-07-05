println ">" * 50
println "Loaded runtime.groovy"
println "<" * 50

dataSource {
    username = "sa"
    //password = "password"
    password = UppercaseCodec.decode("PASSWORD")
}
