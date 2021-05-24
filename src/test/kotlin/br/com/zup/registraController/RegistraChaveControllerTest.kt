package br.com.zup.registraController

import br.com.zup.PixKeymanagerRegistraGrpcServiceGrpc
import br.com.zup.PixResponse
import br.com.zup.factory.RestFactory
import br.com.zup.registra.RegistraRequest
import br.com.zup.registra.TipoChave
import br.com.zup.registra.TipoConta
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*
import java.util.stream.Stream
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
class RegistraChaveControllerTest {


    @field:Inject
    lateinit var registraStub: PixKeymanagerRegistraGrpcServiceGrpc.PixKeymanagerRegistraGrpcServiceBlockingStub


    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient


    @ParameterizedTest
    @MethodSource("retornaTipoChave")
    internal fun `deve cadastrar chave`(chave:String, tipoChave: TipoChave) {

        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val responseGrpc = PixResponse.newBuilder()
            .setId(pixId)
            .build()

        given(registraStub.adicionar(Mockito.any())).willReturn(responseGrpc)

        val chave = RegistraRequest(
            tipoConta = TipoConta.CONTA_CORRENTE,
            chave = chave,
            tipoChave = tipoChave
        )

        val request = HttpRequest.POST("registra/chaves/$clienteId", chave)
        val response = client.toBlocking().exchange(request, RegistraRequest::class.java)

        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location").contains(pixId))

    }

    @Test
    internal fun `deve cadastrar chave aleatória`() {
        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val responseGrpc = PixResponse.newBuilder()
            .setId(pixId)
            .build()

        given(registraStub.adicionar(Mockito.any())).willReturn(responseGrpc)

        val chave = RegistraRequest(
            tipoConta = TipoConta.CONTA_CORRENTE,
            chave = " ",
            tipoChave = TipoChave.ALEATORIA
        )

        val request = HttpRequest.POST("registra/chaves/$clienteId", chave)
        val response = client.toBlocking().exchange(request, RegistraRequest::class.java)

        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location").contains(pixId))
    }

    @Factory
    @Replaces(factory = RestFactory::class)
    internal class MockitoStubFactory {
        @Singleton
        fun stubMock() = Mockito.mock(PixKeymanagerRegistraGrpcServiceGrpc.PixKeymanagerRegistraGrpcServiceBlockingStub::class.java)
    }


    companion object{

        @JvmStatic
        fun retornaTipoChave(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of("46431186877",TipoChave.CPF ),
                Arguments.of("+5585988714077",TipoChave.CELULAR),
                Arguments.of("victor@email.com",TipoChave.EMAIL),
            )
        }

    }

}