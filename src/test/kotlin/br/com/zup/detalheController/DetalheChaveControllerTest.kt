package br.com.zup.detalheController

import br.com.zup.*
import br.com.zup.factory.RestFactory
import br.com.zup.registra.RegistraRequest
import com.google.protobuf.Timestamp
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
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
class DetalheChaveControllerTest {


    @field:Inject
    lateinit var consultaStub: PixKeyManagerConsultaServiceGrpc.PixKeyManagerConsultaServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    val criadaEm = LocalDateTime.now()


    @Test
    internal fun `deve mostrar detalhes da chave passando clienteId e PixId`() {
        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val responseGrpc = ConsultaPixResponse.newBuilder()
            .setClienteId(clienteId)
            .setPixId(pixId)
            .setChave(ConsultaPixResponse.ChavePix
                .newBuilder()
                .setTipoChave(TipoChave.ALEATORIA)
                .setChave("91381ed1-ac3b-4d44-bba0-f9e75a3f9867")
                .setDadosConta(ConsultaPixResponse.ChavePix.DadosConta.newBuilder()
                    .setTipoConta(TipoConta.CONTA_CORRENTE)
                    .setInstituicao("ITAÚ UNIBANCO S.A.")
                    .setNome("Vitin")
                    .setCpf("144.163.240-90")
                    .setAgencia("0001")
                    .setNumero("291900")
                    .build())
                .setCriadaEm(criadaEm.let {
                    val createdAt = it!!.atZone(ZoneId.of("UTC")).toInstant()
                    Timestamp.newBuilder()
                        .setSeconds(createdAt.epochSecond)
                        .setNanos(createdAt.nano)
                        .build()
                })
            ).build()


        given(consultaStub.consultar(Mockito.any())).willReturn(responseGrpc)

        val request = HttpRequest.GET<Any>("detalhe/chaves/$clienteId/$pixId")
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())

    }

    @Factory
    @Replaces(factory = RestFactory::class)
    internal class MockitoStubFactory {
        @Singleton
        fun stubMock() = Mockito.mock(PixKeyManagerConsultaServiceGrpc.PixKeyManagerConsultaServiceBlockingStub::class.java)
    }
}