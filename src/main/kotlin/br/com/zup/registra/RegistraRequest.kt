package br.com.zup.registra

import br.com.zup.TipoChave
import br.com.zup.TipoConta
import br.com.zup.TipoPessoa

class RegistraRequest(
    val clienteId: String,
    val tipoConta: TipoConta,
    val tipoChave: TipoChave,
    val chave: String,
    val tipoPessoa: TipoPessoa) {
}