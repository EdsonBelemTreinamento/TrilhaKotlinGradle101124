package br.com.arq.service

import br.com.arq.dto.AlunoDTO
import br.com.arq.entity.Aluno
import br.com.arq.entity.AlunoTurma
import br.com.arq.repository.AlunoRepository
import br.com.arq.repository.AlunoTurmaRepository
import br.com.arq.repository.TurmaRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class AlunoService(
        private val alunoRepository: AlunoRepository,
        private val turmaRepository: TurmaRepository,
        private val alunoTurmaRepository: AlunoTurmaRepository
) {

    fun addAlunoWithTurmas(alunoDTO: AlunoDTO): Mono<AlunoDTO> {
        val aluno = alunoDTO.toEntity()

        return alunoRepository.save(aluno)
                .flatMap { savedAluno ->
                    val alunoTurmaFlux = Flux.fromIterable(alunoDTO.turmaIds)
                            .flatMap { turmaId ->
                                turmaRepository.findById(turmaId)
                                        .flatMap { turma ->
                                            alunoTurmaRepository.save(
                                                    AlunoTurma(alunoId = savedAluno.id!!, turmaId = turma.id!!)
                                            )
                                        }
                            }
                    alunoTurmaFlux.then(Mono.just(alunoDTO.copy(id = savedAluno.id)))
                }
    }

    fun listAllAlunos(): Flux<AlunoDTO> {
        return alunoRepository.findAll()
                .flatMap { aluno ->
                    alunoTurmaRepository.findByAlunoId(aluno.id!!)
                            .map { it.turmaId }
                            .collectList()
                            .map { turmaIds ->
                                AlunoDTO(id = aluno.id, nome = aluno.nome, email = aluno.email, turmaIds = turmaIds)
                            }
                }
    }

    fun updateAluno(alunoId: Long, alunoDTO: AlunoDTO): Mono<AlunoDTO> {
        return alunoRepository.findById(alunoId)
                .flatMap { existingAluno ->
                    val updatedAluno = existingAluno.copy(nome = alunoDTO.nome, email = alunoDTO.email)

                    alunoRepository.save(updatedAluno)
                            .flatMap { savedAluno ->
                                alunoTurmaRepository.findByAlunoId(alunoId)
                                        .flatMap { alunoTurmaRepository.delete(it) }
                                        .thenMany(Flux.fromIterable(alunoDTO.turmaIds))
                                        .flatMap { turmaId ->
                                            turmaRepository.findById(turmaId)
                                                    .flatMap { turma ->
                                                        alunoTurmaRepository.save(
                                                                AlunoTurma(alunoId = savedAluno.id!!, turmaId = turma.id!!)
                                                        )
                                                    }
                                        }
                                        .then(Mono.just(alunoDTO.copy(id = savedAluno.id)))
                            }
                }
    }

    fun deleteAluno(alunoId: Long): Mono<Void> {
        return alunoTurmaRepository.findByAlunoId(alunoId)
                .flatMap { alunoTurmaRepository.delete(it) }
                .then(alunoRepository.deleteById(alunoId))
    }
}
