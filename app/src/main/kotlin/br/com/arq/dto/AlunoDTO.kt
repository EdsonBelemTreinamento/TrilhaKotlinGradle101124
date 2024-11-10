package br.com.arq.dto

import br.com.arq.entity.Aluno


data class AlunoDTO(
        val id: Long? = null,
        val nome: String,
        val email: String,
        val turmaIds: List<Long> = listOf()
) {

    fun toEntity(): Aluno {
        return Aluno(
                id = this.id,
                nome = this.nome,
                email = this.email
        )
    }
}