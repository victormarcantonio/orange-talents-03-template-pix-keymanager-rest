package br.com.zup.registra

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator

enum class TipoChave(val tipoChaveGrpc: br.com.zup.TipoChave) {

    CPF(br.com.zup.TipoChave.CPF) {
        override fun valida(chave: String?): Boolean {
            if(chave!!.isBlank()){
                return false
            }

            if(!chave.matches("^[0-9]{11}$".toRegex())){
                return false
            }
            return CPFValidator().run {
                initialize(null)
                isValid(chave,null)
            }
        }
    },
    CELULAR(br.com.zup.TipoChave.CELULAR) {
        override fun valida(chave: String?): Boolean {
            if(chave!!.isBlank()){
                return false
            }

            return chave.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
    },
    EMAIL(br.com.zup.TipoChave.EMAIL) {
        override fun valida(chave: String?): Boolean {
            if(chave!!.isBlank()){
                return false
            }

            return EmailValidator().run{
                initialize(null)
                isValid(chave,null)
            }
        }
    },
    ALEATORIA(br.com.zup.TipoChave.ALEATORIA) {
        override fun valida(chave: String?): Boolean = chave!!.isBlank()

    };



    abstract fun valida(chave: String?): Boolean
}