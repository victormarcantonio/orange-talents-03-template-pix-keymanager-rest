package br.com.zup.registra



enum class TipoConta(val tipoContaGrpc: br.com.zup.TipoConta) {

    CONTA_CORRENTE(br.com.zup.TipoConta.CONTA_CORRENTE),
    CONTA_POUPANCA(br.com.zup.TipoConta.CONTA_POUPANCA)
}