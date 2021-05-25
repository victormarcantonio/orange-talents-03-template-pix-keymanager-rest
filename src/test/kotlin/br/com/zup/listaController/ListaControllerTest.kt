package br.com.zup.listaController

import br.com.zup.*
import br.com.zup.factory.RestFactory
import com.google.protobuf.Timestamp
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
class ListaControllerTest {


    @field:Inject
    lateinit var listaStub: PixKeyManagerListaServiceGrpc.PixKeyManagerListaServiceBlockingStub


    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    val criadaEm = LocalDateTime.now()


    @Test
    internal fun `deve listar chaves`() {
        val clienteId = UUID.randomUUID().toString()

        val responseGrpc = listaChaveResponse(clienteId = UUID.fromString(clienteId))


        given(listaStub.listar(Mockito.any())).willReturn(responseGrpc)


        val request = HttpRequest.GET<Any>("lista/chaves/$clienteId")
        val response = client.toBlocking().exchange(request, List::class.java)


        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertEquals(1, response.body()!!.size)

    }


    fun listaChaveResponse(clienteId:UUID): ListaPixResponse{
       val chave =  ListaPixResponse.Chave.newBuilder()
           .setPixId("6389f1a5-1339-4ef0-acab-60d0b24a017d")
           .setTipoChave(TipoChave.ALEATORIA)
           .setChave("7d5f6ab8-dd1b-480c-b381-f00626a9c42b")
           .setTipoConta(TipoConta.CONTA_CORRENTE)
           .setCriadaEm(criadaEm.let {
               val createdAt = it!!.atZone(ZoneId.of("UTC")).toInstant()
               Timestamp.newBuilder()
                   .setSeconds(createdAt.epochSecond)
                   .setNanos(createdAt.nano)
                   .build()
           }).build()


        return ListaPixResponse.newBuilder()
            .setClienteId(clienteId.toString())
            .addAllChaves(listOf(chave))
            .build()
    }

    @Factory
    @Replaces(factory = RestFactory::class)
    internal class MockitoStubFactory {
        @Singleton
        fun stubMock() = Mockito.mock(PixKeyManagerListaServiceGrpc.PixKeyManagerListaServiceBlockingStub::class.java)
    }
}