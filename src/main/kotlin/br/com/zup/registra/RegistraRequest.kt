package br.com.zup.registra

import br.com.zup.PixRequest
import br.com.zup.TipoPessoa
import br.com.zup.validation.ValidaChave
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidaChave
@Introspected
data class RegistraRequest(
    @field: NotNull val tipoConta: TipoConta?,
    @field: NotNull val tipoChave: TipoChave?,
    @field: Size(max = 77) val chave: String?) {


    fun toGrpc(clienteId: UUID): PixRequest{
        return PixRequest.newBuilder()
            .setId(clienteId.toString())
            .setTipoConta(tipoConta?.tipoContaGrpc ?: br.com.zup.TipoConta.CONTA_DEFAULT)
            .setTipoChave(tipoChave?.tipoChaveGrpc ?: br.com.zup.TipoChave.CHAVE_DEFAULT)
            .setTipoPessoa(TipoPessoa.NATURAL_PERSON)
            .setChave(chave ?: "")
            .build()
    }
}