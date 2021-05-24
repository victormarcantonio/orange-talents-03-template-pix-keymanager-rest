package br.com.zup.validation

import br.com.zup.registra.RegistraRequest
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint

import javax.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidaChavePix::class])
@MustBeDocumented
annotation class ValidaChave(
    val message: String = "Chave pix inválida",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)


@Singleton
class ValidaChavePix : ConstraintValidator<ValidaChave, RegistraRequest>{
    override fun isValid(
        value: RegistraRequest?,
        annotationMetadata: AnnotationValue<ValidaChave>,
        context: ConstraintValidatorContext
    ): Boolean {
        if(value?.tipoChave== null){
            return false
        }
        return value.tipoChave.valida(value.chave)

    }
}
