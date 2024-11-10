package br.com.arq.service

import br.com.arq.dto.TurmaDTO
import br.com.arq.entity.AlunoTurma
import br.com.arq.entity.Turma
import br.com.arq.repository.AlunoRepository
import br.com.arq.repository.AlunoTurmaRepository
import br.com.arq.repository.TurmaRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class TurmaService(
        private val turmaRepository: TurmaRepository,
        private val alunoRepository: AlunoRepository,
        private val alunoTurmaRepository: AlunoTurmaRepository
) {


    fun addTurmaWithAlunos(turmaDTO: TurmaDTO): Mono<TurmaDTO> {
        val turma = turmaDTO.toEntity()

        return turmaRepository.save(turma)
                .flatMap { savedTurma ->
                    val alunoTurmaFlux = Flux.fromIterable(turmaDTO.turmaIds)
                            .flatMap { alunoId ->
                                alunoRepository.findById(alunoId)
                                        .flatMap { aluno ->
                                            alunoTurmaRepository.save(
                                                    AlunoTurma(alunoId = aluno.id!!, turmaId = savedTurma.id!!)
                                            )
                                        }
                            }
                    alunoTurmaFlux.then(Mono.just(turmaDTO.copy(id = savedTurma.id)))
                }
    }


    fun listAllTurmas(): Flux<TurmaDTO> {
        return turmaRepository.findAll()
                .flatMap { turma ->
                    alunoTurmaRepository.findByTurmaId(turma.id!!)
                            .map { it.alunoId }
                            .collectList()
                            .map { alunoIds ->
                                TurmaDTO(id = turma.id, nome = turma.nome, turmaIds = alunoIds)
                            }
                }
    }


    fun updateTurma(turmaId: Long, turmaDTO: TurmaDTO): Mono<TurmaDTO> {
        return turmaRepository.findById(turmaId)
                .flatMap { existingTurma ->
                    val updatedTurma = existingTurma.copy(nome = turmaDTO.nome)

                    turmaRepository.save(updatedTurma)
                            .flatMap { savedTurma ->
                                alunoTurmaRepository.findByTurmaId(turmaId)
                                        .flatMap { alunoTurmaRepository.delete(it) }
                                        .thenMany(Flux.fromIterable(turmaDTO.turmaIds))
                                        .flatMap { alunoId ->
                                            alunoRepository.findById(alunoId)
                                                    .flatMap { aluno ->
                                                        alunoTurmaRepository.save(
                                                                AlunoTurma(alunoId = aluno.id!!, turmaId = savedTurma.id!!)
                                                        )
                                                    }
                                        }
                                        .then(Mono.just(turmaDTO.copy(id = savedTurma.id)))
                            }
                }
    }


    fun deleteTurma(turmaId: Long): Mono<Void> {
        return alunoTurmaRepository.findByTurmaId(turmaId)
                .flatMap { alunoTurmaRepository.delete(it) }
                .then(turmaRepository.deleteById(turmaId))
    }
}
