package br.com.zup.factory

import br.com.zup.PixKeyManagerConsultaServiceGrpc
import br.com.zup.PixKeyManagerListaServiceGrpc
import br.com.zup.PixKeyManagerRemoveGrpcServiceGrpc
import br.com.zup.PixKeymanagerRegistraGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class RestFactory(@GrpcChannel("keyManagerGrpc") val channel: ManagedChannel) {

    @Singleton
    fun registraChave() = PixKeymanagerRegistraGrpcServiceGrpc.newBlockingStub(channel)


    @Singleton
    fun removeChave() = PixKeyManagerRemoveGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun consultaChave()= PixKeyManagerConsultaServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaChave()= PixKeyManagerListaServiceGrpc.newBlockingStub(channel)
}