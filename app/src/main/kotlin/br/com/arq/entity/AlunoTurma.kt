package br.com.arq.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("aluno_turma")
data class AlunoTurma(
        @Id val id: Long? = null,
        val alunoId: Long,
        val turmaId: Long,
)