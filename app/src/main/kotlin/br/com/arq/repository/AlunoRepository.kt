package br.com.arq.repository

import br.com.arq.entity.Aluno
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface AlunoRepository : ReactiveCrudRepository<Aluno, Long> {
    fun findByEmail(email: String): Flux<Aluno>
}