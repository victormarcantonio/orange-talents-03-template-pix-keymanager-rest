package br.com.zup.removeController

import br.com.zup.PixKeyManagerRemoveGrpcServiceGrpc
import br.com.zup.PixKeymanagerRegistraGrpcServiceGrpc
import br.com.zup.PixResponse
import br.com.zup.RemovePixResponse
import br.com.zup.factory.RestFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@MicronautTest
class RemoveChaveControllerTest {

    @field:Inject
    lateinit var removeStub: PixKeyManagerRemoveGrpcServiceGrpc.PixKeyManagerRemoveGrpcServiceBlockingStub


    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun `deve remover chave`() {

        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val responseGrpc = RemovePixResponse.newBuilder()
            .setClienteId("")
            .setPixId("")
            .build()


        given(removeStub.deletar(Mockito.any())).willReturn(responseGrpc)


        val request = HttpRequest.DELETE<Any>("remove/chaves/$clienteId/$pixId")
        val response = client.toBlocking().exchange(request, Any::class.java)


        assertEquals(HttpStatus.OK, response.status)

    }


    @Factory
    @Replaces(factory = RestFactory::class)
    internal class MockitoStubFactory {
        @Singleton
        fun stubMock() = Mockito.mock(PixKeyManagerRemoveGrpcServiceGrpc.PixKeyManagerRemoveGrpcServiceBlockingStub::class.java)
    }

}