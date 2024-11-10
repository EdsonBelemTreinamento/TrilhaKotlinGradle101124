package br.com.arq.dto


import br.com.arq.entity.Turma

data class TurmaDTO(
        val id: Long? = null,
        val nome: String,
        val turmaIds: List<Long> = listOf()
) {

    fun toEntity(): Turma {
        return Turma(
                id = this.id,
                nome = this.nome
        )
    }
}
