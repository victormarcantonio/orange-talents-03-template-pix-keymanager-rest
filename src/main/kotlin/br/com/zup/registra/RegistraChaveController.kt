package br.com.zup.registra

import br.com.zup.PixKeymanagerRegistraGrpcServiceGrpc
import br.com.zup.PixRequest
import br.com.zup.TipoChave
import br.com.zup.TipoConta
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post


@Controller
class RegistraChaveController(val grpcClient: PixKeymanagerRegistraGrpcServiceGrpc.PixKeymanagerRegistraGrpcServiceBlockingStub) {


    @Post("/registra/chaves")
    fun registra(@Body registraRequest: RegistraRequest): HttpResponse<Any>{


        val request = PixRequest.newBuilder()
            .setId(registraRequest.clienteId)
            .setTipoConta(registraRequest.tipoConta)
            .setTipoChave(registraRequest.tipoChave)
            .setChave(registraRequest.chave)
            .setTipoPessoa(registraRequest.tipoPessoa)
            .build()


        val response = grpcClient.adicionar(request)
        return HttpResponse.ok()

    }
}