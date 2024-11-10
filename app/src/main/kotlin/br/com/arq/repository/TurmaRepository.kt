package br.com.arq.repository

import br.com.arq.entity.Turma
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface TurmaRepository : ReactiveCrudRepository<Turma, Long> {
    fun findByNome(nome: String): Flux<Turma>
}