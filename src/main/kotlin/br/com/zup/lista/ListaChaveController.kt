package br.com.zup.lista

import br.com.zup.ListaPixRequest
import br.com.zup.PixKeyManagerListaServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.validation.Validated
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Validated
@Controller("/lista")
class ListaChaveController(val grpcClient: PixKeyManagerListaServiceGrpc.PixKeyManagerListaServiceBlockingStub) {

    @Get("/chaves/{clienteId}")
    fun lista(@NotNull clienteId: UUID): HttpResponse<Any>{
        val request = ListaPixRequest.newBuilder()
           .setClienteId(clienteId.toString())
           .build()

        val response = grpcClient.listar(request)

        val chaves = response.chavesList.map {
            ListaChaveResponse(it)
        }

        return HttpResponse.ok(chaves)
    }
}