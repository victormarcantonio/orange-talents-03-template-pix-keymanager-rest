package br.com.zup.handler

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class GlobalExceptionHandlerTest {

    val request = HttpRequest.GET<Any>("/")

    @Test
    internal fun `deve retornar 404 quando statusException for notFound`() {
        val mensagem = "não encontrado"
        val exception = StatusRuntimeException(Status.NOT_FOUND
            .withDescription(mensagem))

        val resposta = RestErrorHandler().handle(request, exception)

        assertEquals(HttpStatus.NOT_FOUND, resposta.status)
        assertNotNull(resposta.body())
        assertEquals(mensagem, (resposta.body() as JsonError).message)
    }

    @Test
    internal fun `deve retornar 400 quando status Exception for Invalid Argument`() {
        val exception = StatusRuntimeException(Status.INVALID_ARGUMENT
            .withDescription("Dados da requisição estão inválidos"))

        val resposta = RestErrorHandler().handle(request, exception)

        assertEquals(HttpStatus.BAD_REQUEST, resposta.status)
        assertNotNull(resposta.body())
        assertEquals("Dados da requisição estão inválidos", (resposta.body() as JsonError).message)
    }

    @Test
    internal fun `deve retornar 422 quando status Exception for Already Exists`() {

        val mensagem = "chave já existe"
        val exception = StatusRuntimeException(Status.ALREADY_EXISTS
            .withDescription(mensagem))

        val resposta = RestErrorHandler().handle(request, exception)
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, resposta.status)
        assertNotNull(resposta.body())
        assertEquals("chave já existe", (resposta.body() as JsonError).message)
    }

    @Test
    internal fun `deve retornar erro 500 caso seja um erro desconhecido`() {
        val mensagem = "Nao foi possivel completar a requisição devido ao erro teste teste"

        val exception = StatusRuntimeException(Status.INTERNAL
            .withDescription(mensagem))

        val resposta = RestErrorHandler().handle(request, exception)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resposta.status)
    }
}