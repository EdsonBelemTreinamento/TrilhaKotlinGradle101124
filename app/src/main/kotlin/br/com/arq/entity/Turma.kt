package br.com.arq.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("turmas")
data class Turma(
        @Id val id: Long? = null,
        val nome: String,
)