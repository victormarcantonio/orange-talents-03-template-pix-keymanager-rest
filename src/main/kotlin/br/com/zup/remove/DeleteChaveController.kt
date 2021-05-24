package br.com.zup.remove

import br.com.zup.PixKeyManagerRemoveGrpcServiceGrpc
import br.com.zup.PixKeymanagerRegistraGrpcServiceGrpc
import br.com.zup.RemovePixRequest
import br.com.zup.factory.RestFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Singleton

@Validated
@Controller("/remove")
class DeleteChaveController(val grpcClient: PixKeyManagerRemoveGrpcServiceGrpc.PixKeyManagerRemoveGrpcServiceBlockingStub) {


    @Delete("chaves/{clienteId}/{pixId}")
    fun remove(clienteId: UUID, pixId: UUID): HttpResponse<Any>{

        val request = RemovePixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setPixId(pixId.toString())
            .build()

        grpcClient.deletar(request)

        return HttpResponse.ok()
    }


}