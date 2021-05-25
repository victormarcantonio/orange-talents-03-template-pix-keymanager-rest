package br.com.zup.consulta

import br.com.zup.ConsultaPixRequest
import br.com.zup.PixKeyManagerConsultaServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.validation.Validated
import java.util.*
import javax.validation.Valid

@Validated
@Controller("/detalhe")
class DetalheChaveController(val grpcClient: PixKeyManagerConsultaServiceGrpc.PixKeyManagerConsultaServiceBlockingStub) {

    @Get("/chaves/{pixId}/{clienteId}")
    fun detalhe(pixId: UUID, clienteId: UUID): HttpResponse<Any>{
        val request = ConsultaPixRequest
            .newBuilder()
            .setPixId(
                ConsultaPixRequest.FiltroPixPorId.newBuilder()
                    .setClienteId(clienteId.toString())
                    .setPixId(pixId.toString())
                    .build()
            ).build()
        val response = grpcClient.consultar(request)

        return HttpResponse.ok(DetalheChaveResponse(response))
    }


}