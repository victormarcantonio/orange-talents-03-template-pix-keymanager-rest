package br.com.zup.controller

import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class RegistraChaveControllerTest {


    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient


    @Test
    internal fun `deve cadastrar chave`() {

    }
}