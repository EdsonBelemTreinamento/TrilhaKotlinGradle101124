package br.com.arq.repository

import br.com.arq.entity.AlunoTurma
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface AlunoTurmaRepository : ReactiveCrudRepository<AlunoTurma, Long> {
    fun findByAlunoId(alunoId: Long): Flux<AlunoTurma>
    fun findByTurmaId(turmaId: Long): Flux<AlunoTurma>
}