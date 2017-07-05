import groovy.util.logging.Slf4j

@Slf4j
class UppercaseCodec {
    static encode = { theTarget ->
        def encoded = theTarget.toString().toUpperCase()
        log.info "Encoded: $theTarget -> $encoded"
        return encoded
    }

    static decode = { theTarget ->
        def decoded = theTarget.toString().toLowerCase()
        log.info "Decoded: $theTarget -> $decoded"
        return decoded
    }
}
