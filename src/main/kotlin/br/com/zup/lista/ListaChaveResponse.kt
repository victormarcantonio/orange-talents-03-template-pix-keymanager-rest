package br.com.zup.lista

import br.com.zup.ListaPixResponse
import br.com.zup.registra.TipoChave
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Introspected
class ListaChaveResponse(chaveResponse: ListaPixResponse.Chave) {


    val pixId = chaveResponse.pixId
    val tipoChave = chaveResponse.tipoChave
    val chave = chaveResponse.chave
    val tipoConta = chaveResponse.tipoConta
    val criadaEm = chaveResponse.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }
}