package br.com.zup.registra

import br.com.zup.PixKeymanagerRegistraGrpcServiceGrpc
import br.com.zup.PixRequest
import br.com.zup.TipoChave
import br.com.zup.TipoConta
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import java.util.*
import javax.validation.Valid

@Validated
@Controller
class RegistraChaveController(val grpcClient: PixKeymanagerRegistraGrpcServiceGrpc.PixKeymanagerRegistraGrpcServiceBlockingStub) {


    @Post("/registra/chaves/{clienteId}")
    fun registra(clienteId: UUID,@Valid @Body registraRequest: RegistraRequest): HttpResponse<Any>{


        val request = registraRequest.toGrpc(clienteId)

        val response = grpcClient.adicionar(request)

        return HttpResponse.created(HttpResponse.uri("/registra/chaves/$clienteId/${response.id}"))

    }
}