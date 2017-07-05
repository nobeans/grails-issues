println ">" * 50
println "Loaded external-config.groovy"
println "<" * 50

dataSource.password = password = UppercaseCodec.decode("HOGE")
