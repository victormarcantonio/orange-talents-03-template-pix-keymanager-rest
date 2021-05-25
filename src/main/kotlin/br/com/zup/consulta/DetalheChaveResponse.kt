package br.com.zup.consulta

import br.com.zup.ConsultaPixResponse
import br.com.zup.registra.TipoChave
import br.com.zup.registra.TipoConta
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Introspected
class DetalheChaveResponse(chaveResponse: ConsultaPixResponse) {


    val pixId = chaveResponse.pixId
    val tipo = chaveResponse.chave.tipoChave
    val chave = chaveResponse.chave.chave

    val criadaEm = chaveResponse.chave.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }

    val tipoConta = when(chaveResponse.chave.dadosConta.tipoConta){
        br.com.zup.TipoConta.CONTA_CORRENTE -> "CONTA_CORRENTE"
        br.com.zup.TipoConta.CONTA_POUPANCA -> "CONTA_POUPANCA"
        else -> "CONTA_DEFAULT"
    }

    val conta = mapOf(Pair("tipo", tipoConta),
             Pair("instituicao", chaveResponse.chave.dadosConta.instituicao),
             Pair("titular", chaveResponse.chave.dadosConta.nome),
             Pair("cpfTitular", chaveResponse.chave.dadosConta.cpf),
             Pair("agencia", chaveResponse.chave.dadosConta.agencia),
             Pair("numero", chaveResponse.chave.dadosConta.numero))
}



