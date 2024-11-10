package br.com.arq.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("alunos")
data class Aluno(
        @Id val id: Long? = null,
        val nome: String,
        val email:String,
)